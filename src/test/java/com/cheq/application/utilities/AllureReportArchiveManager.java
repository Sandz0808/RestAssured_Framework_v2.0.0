package com.cheq.application.utilities;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class AllureReportArchiveManager {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Path ARCHIVE_ROOT =
            Paths.get("reports", "allure");

    public static void cleanupExpiredReports(int retentionDays) {

        if (retentionDays <= 0) {
            System.out.println(
                    "Allure archive cleanup skipped. " +
                            "Retention days must be greater than 0."
            );
            return;
        }

        if (!Files.exists(ARCHIVE_ROOT)) {
            return;
        }

        LocalDate cutoffDate =
                LocalDate.now().minusDays(retentionDays - 1);

        try (Stream<Path> paths = Files.list(ARCHIVE_ROOT)) {

            paths.filter(Files::isDirectory)
                    .forEach(directory -> {

                        String folderName =
                                directory.getFileName().toString();

                        try {

                            LocalDate folderDate =
                                    LocalDate.parse(
                                            folderName,
                                            DATE_FORMAT
                                    );

                            if (folderDate.isBefore(cutoffDate)) {

                                deleteDirectory(directory);

                                System.out.println(
                                        "Deleted expired Allure archive: "
                                                + directory
                                );
                            }

                        } catch (Exception ignored) {
                            // Ignore folders that are not date-based archives
                        }
                    });

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to clean expired Allure archives.",
                    e
            );
        }
    }

    private static void deleteDirectory(Path directory)
            throws IOException {

        try (Stream<Path> paths = Files.walk(directory)) {

            paths.sorted((a, b) ->
                            b.compareTo(a))
                    .forEach(path -> {

                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(
                                    "Failed to delete: " + path,
                                    e
                            );
                        }
                    });
        }
    }

    public static void main(String[] args) {

        int retentionDays = Integer.parseInt(args[0]);

        cleanupExpiredReports(retentionDays);
    }
}