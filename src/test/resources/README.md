# REST Assured API Automation Framework

## Overview

A Java-based REST Assured API automation framework using TestNG for API test execution, JSON-based test data, reusable request specifications and assertions, schema validation, retry handling, parallel execution, environment configuration, logging, and Allure reporting.

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Dependencies](#dependencies)
- [Framework Structure](#framework-structure)
- [Configuration and Environment](#configuration-and-environment)
- [Test Data](#test-data)
- [Running Tests](#running-tests)
- [TestNG Groups](#testng-groups)
- [Parallel Execution](#parallel-execution)
- [Allure Reporting](#allure-reporting)
- [Schema Validation](#schema-validation)
- [Logs and Response Files](#logs-and-response-files)
- [Troubleshooting](#troubleshooting)

---

## Prerequisites

Install the following before running the framework:

| Tool | Purpose |
|---|---|
| Java JDK | Compile and execute the framework |
| IntelliJ IDEA | Open and run the project |
| Maven | Build and dependency management |
| Git | Source control |
| Allure CLI | Generate and open Allure reports |

### Verify Java

```bash
java -version
```

### Verify `JAVA_HOME`

```bash
echo %JAVA_HOME%
```

### Verify Maven

```bash
mvn -version
```

### Verify Git

```bash
git --version
```

### Verify Allure

```bash
allure --version
```

---

## Installation and Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
```

Navigate to the project:

```bash
cd <project-folder>
```

### 2. Open the Project

Open the project in IntelliJ IDEA.

Make sure IntelliJ recognizes the project as a Maven project.

### 3. Install Dependencies

Run:

```bash
mvn clean install
```

Maven downloads the dependencies defined in `pom.xml`.

---

## Dependencies

The framework uses Maven for dependency management.

Main dependencies include:

| Dependency | Purpose |
|---|---|
| REST Assured | REST API automation |
| TestNG | Test execution |
| Jackson | JSON processing |
| JSON Schema Validator | Response schema validation |
| Allure TestNG | Allure reporting |
| SLF4J | Logging |

Dependency configuration is maintained in:

```text
pom.xml
```

---

## Framework Structure

```text
src
├── main
│   └── java
│       └── com.cheq.application
│
└── test
    ├── java
    │   └── com.cheq.application
    │
    └── resources
        ├── testdata
        └── schemas
```

### Main Components

| Component | Purpose |
|---|---|
| `tests` | API test cases |
| `services` | API request execution |
| `payloads` | Request payload creation |
| `models` | Request models / POJOs |
| `assertions` | Common and schema assertions |
| `specifications` | Reusable REST Assured request specifications |
| `utilities` | Reusable framework utilities |
| `listeners` | TestNG listeners and retry mechanism |
| `constants` | Centralized constants |
| `testdata` | JSON test data |
| `schemas` | JSON response schemas |

---

## Configuration and Environment

The framework uses configuration values for environment-specific settings such as:

- Base URL
- API keys
- Environment values
- Other API configuration

Configuration values are accessed through:

```text
ConfigReader
```

Before execution, verify that the required configuration values are correctly configured.

---

## Test Data

Test data is stored under:

```text
src/test/resources/testdata/
```

The framework supports JSON-based test data for positive, negative, and parameterized testing.

### Example JSON Test Data

```json
[
  {
    "scenario": "ValidCredential",
    "email": "sjimena@faker.com",
    "password": "12345qwert",
    "expectedStatusCode": 200
  },
  {
    "scenario": "EmptyEmail",
    "email": "",
    "password": "12345qwert",
    "expectedStatusCode": 401
  },
  {
    "scenario": "EmptyPassword",
    "email": "sjimena@faker.com",
    "password": "",
    "expectedStatusCode": 401
  }
]
```

### JSON Reader

JSON test data is accessed through:

```text
JsonReaderUtil
```

The utility supports:

- JSON objects
- JSON lists / arrays
- Nested JSON objects

### Data-Driven Testing

TestNG `@DataProvider` is used to execute the same test against multiple test-data scenarios.

Example:

```java
@DataProvider(name = "loginTestData")
public Object[][] loginTestData() {
    // Test data
}
```

The test consumes the data using:

```java
@Test(dataProvider = "loginTestData")
```

This allows one test method to execute multiple scenarios.

---

## Running Tests

### Run a Single Test

In IntelliJ IDEA:

1. Open the required test class.
2. Locate the `@Test` method.
3. Right-click the test.
4. Select **Run**.

### Run a Test Class

Right-click the test class and select:

```text
Run '<TestClassName>'
```

### Run the TestNG Suite

The framework uses:

```text
testng.xml
```

Open `testng.xml` and run the suite from IntelliJ IDEA.

### Run Using Maven

Run the tests:

```bash
mvn test
```

For a clean execution:

```bash
mvn clean test
```

---

## TestNG Groups

Tests can be assigned to TestNG groups.

Example:

```java
@Test(
    groups = {"smoke", "auth", "test"}
)
```

Common groups used in the framework include:

```text
smoke
auth
contact
test
```

Groups allow specific categories of tests to be executed independently.

---

## Parallel Execution

Parallel execution is configured through:

```text
testng.xml
```

TestNG can execute multiple tests concurrently using multiple workers or threads.

To verify parallel execution:

1. Run the TestNG suite.
2. Check the execution logs.
3. Observe whether multiple tests execute concurrently.

---

## Allure Reporting

The framework uses Allure for test execution reporting.

### Generate Allure Report

After test execution:

```bash
allure generate reports/allure-results --single-file --output reports/allure-reports --clean
```

The `--clean` option removes previously generated report data before creating the new report.

### Open Allure Report

```bash
allure open reports/allure-reports
```

### Allure Test Organization

Tests can use Allure annotations:

```java
@Epic("Contact List API Testing")
@Feature("Authentication")
@Story("Create Contact")
```

These annotations organize tests in the Allure report.

---

## Schema Validation

JSON response schemas are stored under:

```text
src/test/resources/schemas/
```

Schema validation verifies the structure and data types of an API response.

Example:

```java
SchemaAssertions.verifySchema(
    response,
    SchemaAssertions.SchemaType.CREATE_CONTACT_SCHEMA
);
```

---

## Logs and Response Files

The framework provides reusable utilities for logging and response handling.

### Logging

Logging is handled through:

```text
LoggerUtil
```

Logs can provide information such as:

- HTTP method
- Endpoint
- Status code
- Response time
- Test execution information

### Response Files

Response handling is managed through:

```text
SaveResponseUtil
```

Negative test scenarios should still return the API response so that the test can validate the expected error status.

---

## Troubleshooting

### Maven Dependencies Are Not Resolved

Run:

```bash
mvn clean install
```

Then reload the Maven project in IntelliJ IDEA.

### Allure Shows Previous Results

Generate the report using:

```bash
allure generate reports/allure-results --single-file --output reports/allure-reports --clean
```

### JSON Test Data Cannot Be Read

Verify:

1. The JSON file location.
2. The filename.
3. The JSON structure.
4. The property name.
5. The array index when using list-based test data.

### Schema Validation Fails

Verify:

1. The API response.
2. The expected schema.
3. Required fields.
4. Data types.
5. Nested object structure.

### Negative Test Fails Before Assertion

The Service layer should return the API response instead of failing the test based only on the HTTP status code.

Assertions should determine whether the returned response matches the expected result.
