from __future__ import annotations

import json
import sys
from collections import Counter, defaultdict
from datetime import datetime, timezone
from pathlib import Path
from typing import Any


VALID_STATUSES = {"passed", "failed", "broken", "skipped", "unknown"}


def parse_labels(result: dict[str, Any]) -> dict[str, str]:
    labels: dict[str, str] = {}

    for label in result.get("labels") or []:
        name = str(label.get("name") or "").strip()
        value = str(label.get("value") or "").strip()

        if name and value:
            labels[name] = value

    return labels


def first_label(labels: dict[str, str], *names: str) -> str | None:
    for name in names:
        if labels.get(name):
            return labels[name]

    return None


def parameter_key(
        result: dict[str, Any],
        exclude_browser: bool = False,
) -> tuple[tuple[str, str], ...]:
    parameters: list[tuple[str, str]] = []

    for parameter in result.get("parameters") or []:
        name = str(parameter.get("name") or "").strip()
        value = str(parameter.get("value") or "").strip()

        if not name:
            continue

        if exclude_browser and name.lower() == "browser":
            continue

        parameters.append((name, value))

    return tuple(sorted(parameters))


def logical_test_key(
        result: dict[str, Any],
        labels: dict[str, str],
) -> tuple[Any, ...]:
    return (
        result.get("fullName") or result.get("name") or "unknown-test",
        labels.get("feature"),
        labels.get("story"),
        parameter_key(result, exclude_browser=True),
    )


def retry_group_key(
        result: dict[str, Any],
        labels: dict[str, str],
) -> tuple[Any, ...]:
    history_id = result.get("historyId")

    if history_id:
        return (str(history_id),)

    return (
        result.get("fullName") or result.get("name") or "unknown-test",
        labels.get("feature"),
        labels.get("story"),
        parameter_key(result),
    )


def load_results(results_directory: Path) -> list[dict[str, Any]]:
    results: list[dict[str, Any]] = []

    for result_file in sorted(results_directory.rglob("*-result.json")):
        try:
            with result_file.open("r", encoding="utf-8") as file:
                result = json.load(file)

        except (OSError, json.JSONDecodeError) as error:
            print(
                f"Skipping invalid result file "
                f"{result_file}: {error}"
            )
            continue

        labels = parse_labels(result)

        status = str(
            result.get("status") or "unknown"
        ).lower()

        if status not in VALID_STATUSES:
            status = "unknown"

        results.append(
            {
                "raw": result,
                "labels": labels,
                "status": status,
                "logical_key": logical_test_key(
                    result,
                    labels,
                ),
                "retry_key": retry_group_key(
                    result,
                    labels,
                ),
                "start_ms": result.get("start"),
                "stop_ms": result.get("stop"),
            }
        )

    return results


def load_environment(
        results_directory: Path,
) -> dict[str, str]:
    """
    Reads Allure environment.properties.

    Expected file:
        target/allure-results/environment.properties

    Example:
        Environment=QA
        Browser=Chrome
        BaseURL=https://example.com
    """

    environment_file = (
            results_directory / "environment.properties"
    )

    if not environment_file.is_file():
        print(
            "No environment.properties found. "
            "Environment will be empty."
        )
        return {}

    environment: dict[str, str] = {}

    try:
        with environment_file.open(
                "r",
                encoding="utf-8",
        ) as file:

            for line in file:
                line = line.strip()

                if (
                        not line
                        or line.startswith("#")
                        or "=" not in line
                ):
                    continue

                key, value = line.split("=", 1)

                key = key.strip()
                value = value.strip()

                if key and value:
                    environment[key] = value

    except OSError as error:
        print(
            f"Unable to read environment.properties: "
            f"{error}"
        )

    return environment


def consolidate_retries(
        raw_results: list[dict[str, Any]],
) -> list[dict[str, Any]]:

    groups: dict[
        tuple[Any, ...],
        list[dict[str, Any]]
    ] = defaultdict(list)

    for result in raw_results:
        groups[result["retry_key"]].append(result)

    final_results: list[dict[str, Any]] = []

    for attempts in groups.values():

        attempts.sort(
            key=lambda attempt: (
                int(attempt["stop_ms"] or 0),
                int(attempt["start_ms"] or 0),
            )
        )

        final_attempt = attempts[-1]

        prior_statuses = {
            attempt["status"]
            for attempt in attempts[:-1]
        }

        status_details = (
                final_attempt["raw"].get("statusDetails")
                or {}
        )

        final_attempt["retry_count"] = max(
            0,
            len(attempts) - 1,
            )

        final_attempt["is_flaky"] = (
                bool(status_details.get("flaky"))
                or (
                        final_attempt["status"] == "passed"
                        and bool(
                    prior_statuses.intersection(
                        {
                            "failed",
                            "broken",
                            "unknown",
                        }
                    )
                )
                )
        )

        final_results.append(final_attempt)

    return final_results


def to_iso_timestamp(
        epoch_milliseconds: Any,
) -> str | None:

    if epoch_milliseconds is None:
        return None

    try:
        value = int(epoch_milliseconds)

    except (TypeError, ValueError):
        return None

    return datetime.fromtimestamp(
        value / 1000,
        tz=timezone.utc,
        ).astimezone().isoformat()


def build_test_record(
        result: dict[str, Any],
) -> dict[str, Any]:

    raw = result["raw"]
    labels = result["labels"]

    start_ms = raw.get("start")
    stop_ms = raw.get("stop")

    duration_ms = None

    if (
            start_ms is not None
            and stop_ms is not None
    ):
        try:
            duration_ms = max(
                0,
                int(stop_ms) - int(start_ms),
                )

        except (TypeError, ValueError):
            duration_ms = None

    status_details = (
            raw.get("statusDetails")
            or {}
    )

    return {
        "historyId": raw.get("historyId"),

        "testCaseName": (
                raw.get("name")
                or "Unnamed test"
        ),

        "fullName": raw.get("fullName"),

        "suite": first_label(
            labels,
            "suite",
            "subSuite",
            "parentSuite",
        ),

        "feature": labels.get("feature"),

        "story": labels.get("story"),

        "severity": labels.get("severity"),

        "owner": labels.get("owner"),

        "status": result["status"],

        "startedAt": to_iso_timestamp(
            start_ms
        ),

        "completedAt": to_iso_timestamp(
            stop_ms
        ),

        "durationMs": duration_ms,

        "retryCount": result["retry_count"],

        "isFlaky": result["is_flaky"],

        "failureMessage": status_details.get(
            "message"
        ),
    }


def build_summary(
        raw_results: list[dict[str, Any]],
        final_results: list[dict[str, Any]],
        environment: dict[str, str],
) -> dict[str, Any]:

    status_counts = Counter(
        result["status"]
        for result in final_results
    )

    start_values = [
        int(result["start_ms"])
        for result in raw_results
        if result["start_ms"] is not None
    ]

    stop_values = [
        int(result["stop_ms"])
        for result in raw_results
        if result["stop_ms"] is not None
    ]

    duration_ms = (
        max(stop_values) - min(start_values)
        if start_values and stop_values
        else 0
    )

    if (
            status_counts["failed"] > 0
            or status_counts["broken"] > 0
    ):
        overall_status = "FAILED"

    elif status_counts["unknown"] > 0:
        overall_status = "UNKNOWN"

    elif (
            status_counts["skipped"]
            == len(final_results)
            and final_results
    ):
        overall_status = "SKIPPED"

    else:
        overall_status = "PASSED"

    return {
        "generatedAt": (
            datetime.now()
            .astimezone()
            .isoformat()
        ),

        "environment": environment,

        "run": {
            "status": overall_status,

            "totalTests": len(
                final_results
            ),

            "uniqueTestCases": len(
                {
                    result["logical_key"]
                    for result in final_results
                }
            ),

            "passed": status_counts["passed"],

            "failed": status_counts["failed"],

            "broken": status_counts["broken"],

            "skipped": status_counts["skipped"],

            "unknown": status_counts["unknown"],

            "durationMs": duration_ms,
        },

        "tests": [
            build_test_record(result)
            for result in final_results
        ],
    }


def main() -> int:

    if len(sys.argv) not in {2, 3}:

        print(
            "Usage: python generate_test_summary.py "
            "<allure-results-directory> [output-json]"
        )

        return 2

    results_directory = Path(
        sys.argv[1]
    )

    output_file = (
        Path(sys.argv[2])
        if len(sys.argv) == 3
        else Path("target")
             / "test-summary.json"
    )

    if not results_directory.is_dir():

        print(
            "Allure results directory not found: "
            f"{results_directory}"
        )

        return 2

    raw_results = load_results(
        results_directory
    )

    environment = load_environment(
        results_directory
    )

    if not raw_results:

        print(
            "No *-result.json files found."
        )

        summary = {
            "generatedAt": (
                datetime.now()
                .astimezone()
                .isoformat()
            ),

            "environment": environment,

            "run": {
                "status": "NO_RESULTS",

                "totalTests": 0,

                "uniqueTestCases": 0,

                "passed": 0,

                "failed": 0,

                "broken": 0,

                "skipped": 0,

                "unknown": 0,

                "durationMs": 0,
            },

            "tests": [],
        }

    else:

        final_results = consolidate_retries(
            raw_results
        )

        summary = build_summary(
            raw_results,
            final_results,
            environment,
        )

    output_file.parent.mkdir(
        parents=True,
        exist_ok=True,
    )

    with output_file.open(
            "w",
            encoding="utf-8",
    ) as file:

        json.dump(
            summary,
            file,
            indent=2,
            ensure_ascii=False,
        )

    print(
        "Test summary generated successfully: "
        f"{output_file}"
    )

    print(
        f"Environment: "
        f"{summary['environment']} | "
        f"Status: "
        f"{summary['run']['status']} | "
        f"Total: "
        f"{summary['run']['totalTests']} | "
        f"Passed: "
        f"{summary['run']['passed']} | "
        f"Failed: "
        f"{summary['run']['failed']} | "
        f"Broken: "
        f"{summary['run']['broken']} | "
        f"Skipped: "
        f"{summary['run']['skipped']}"
    )

    return 0


if __name__ == "__main__":
    raise SystemExit(main())