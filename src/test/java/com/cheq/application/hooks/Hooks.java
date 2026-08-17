package com.cheq.application.hooks;

import com.cheq.application.specifications.RequestSpecs;
import com.cheq.application.utilities.AllureUtil;
import com.cheq.application.utilities.ConfigReader;
import com.cheq.application.utilities.LoggerUtil;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.LinkedHashMap;
import java.util.Map;

public class Hooks extends RequestSpecs {

    private static final Logger log = LoggerUtil.getLogger(Hooks.class);

    /**
     * Environment used by the complete test execution.
     */
    private static String environment;


    // ==========================================================
    // BEFORE SUITE
    // ==========================================================

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

        System.out.println(">>> BEFORE SUITE EXECUTED <<<");

        log.info("======================================================");
        log.info("STARTING API AUTOMATION FRAMEWORK");
        log.info("======================================================");

        /*
         * Environment
         *
         * Supports:
         * -Denv=staging
         *
         * Also supports the existing:
         * -Dconfig=staging
         */
        environment = System.getProperty(
                "env",
                System.getProperty("config", "staging")
        );

        // Load environment configuration
        ConfigReader.loadProperties(environment);

        log.info("Environment = {}", environment);

        /*
         * Application is NO LONGER determined here.
         *
         * Why?
         *
         * Because we can have:
         *
         * Plaid tests
         * Contact List tests
         *
         * running in the same execution.
         *
         * Each test will determine its own application
         * inside beforeMethod().
         */

        Map<String, String> env = new LinkedHashMap<>();

        env.put("Environment", environment);
        env.put("Framework", "REST Assured Framework v2.0.0");
        env.put("Execution Type", "Local");

        env.put("Java", System.getProperty("java.version"));
        env.put("Operating System", System.getProperty("os.name"));
        env.put("OS Version", System.getProperty("os.version"));
        env.put("Architecture", System.getProperty("os.arch"));

        env.put("Test Framework", "TestNG");
        env.put("HTTP Client", "Rest Assured");
        env.put("Report", "Allure");
        env.put("Logger", "Logback");

        /*
         * Application and Base URL are determined per test,
         * so they are NOT written here.
         */

        AllureUtil.writeAllureEnvironment(env);
        AllureUtil.writeAllureExecutor();
    }


    // ==========================================================
    // BEFORE METHOD
    // ==========================================================

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestResult result) {

        String application = determineApplication(result);

        /*
         * Store application in ThreadLocal.
         *
         * This is critical for parallel execution.
         */
        RequestSpecs.setApplication(application);

        String baseUrlKey = "base.url." + application;
        String url = ConfigReader.get(baseUrlKey);

        log.info(
                "TEST THREAD = {} | TEST CLASS = {}",
                Thread.currentThread().getName(),
                result.getTestClass().getName()
        );

        log.info(
                "Environment = {} | Application = {}",
                environment,
                application
        );

        log.info("Base URL Key = {}", baseUrlKey);
        log.info("URL = {}", url);

        log.info(
                "========== START {} ==========",
                result.getMethod().getMethodName()
        );

        log.info(
                "Executing Test : {}",
                result.getMethod().getMethodName()
        );

        AllureUtil.attachText(
                "Environment:",
                environment
        );

        AllureUtil.attachText(
                "Application:",
                application
        );

        AllureUtil.attachText(
                "Base URL:",
                url
        );

        AllureUtil.attachText(
                "Executing Test:",
                result.getMethod().getMethodName()
        );
    }


    // ==========================================================
    // DETERMINE APPLICATION
    // ==========================================================

    private String determineApplication(ITestResult result) {

        String className = result.getTestClass().getName();

        /*
         * Determine application from package structure.
         *
         * Example:
         *
         * com.cheq.application.tests.plaid...
         *                     ↑
         *                   plaid
         *
         * com.cheq.application.tests.contactlist...
         *                     ↑
         *                 contactlist
         */

        if (className.toLowerCase().contains(".plaid.")) {
            return "plaid";
        }

        if (className.toLowerCase().contains(".contactlist.")) {
            return "contactlist";
        }

        /*
         * Fallback only for tests that don't belong to
         * either application package.
         */
        String application = System.getProperty("app");

        if (application != null && !application.isBlank()) {
            return application;
        }

        throw new IllegalStateException(
                "Unable to determine application for test: "
                        + className
                        + ". "
                        + "Place the test under a supported application package "
                        + "such as '.plaid.' or '.contactlist.', "
                        + "or provide -Dapp."
        );
    }


    // ==========================================================
    // AFTER METHOD
    // ==========================================================

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {

        switch (result.getStatus()) {

            case ITestResult.SUCCESS -> {

                log.info(
                        "PASSED : {}",
                        result.getMethod().getMethodName()
                );

                AllureUtil.attachText(
                        "PASSED:",
                        result.getMethod().getMethodName()
                );
            }

            case ITestResult.FAILURE -> {

                log.error(
                        "FAILED : {}",
                        result.getMethod().getMethodName(),
                        result.getThrowable()
                );

                AllureUtil.attachText(
                        "FAILED:",
                        result.getMethod().getMethodName()
                );

                AllureUtil.attachException(
                        result.getThrowable()
                );
            }

            case ITestResult.SKIP -> {

                log.warn(
                        "SKIPPED : {}",
                        result.getMethod().getMethodName()
                );

                AllureUtil.attachText(
                        "SKIPPED:",
                        result.getMethod().getMethodName()
                );
            }
        }

        /*
         * Very important for parallel execution.
         *
         * Remove the application from the current thread
         * after the test finishes.
         */
        RequestSpecs.clearApplication();
    }


    // ==========================================================
    // AFTER SUITE
    // ==========================================================

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

        log.info("======================================================");
        log.info("FRAMEWORK EXECUTION FINISHED");
        log.info("======================================================");
    }
}