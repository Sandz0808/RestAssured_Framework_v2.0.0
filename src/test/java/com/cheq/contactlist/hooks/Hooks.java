package com.cheq.contactlist.hooks;

import com.cheq.contactlist.specs.RequestSpec;
import com.cheq.contactlist.utils.ApiAllureUtil;
import com.cheq.contactlist.utils.ConfigReader;
import com.cheq.contactlist.utils.LoggerUtil;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.LinkedHashMap;
import java.util.Map;


public class Hooks extends RequestSpec {

    private static final Logger log = LoggerUtil.getLogger(Hooks.class);


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {

        System.out.println(">>> BEFORE SUITE EXECUTED <<<");

        log.info("======================================================");
        log.info("STARTING API AUTOMATION FRAMEWORK");
        log.info("======================================================");

        String environment = System.getProperty("config", "staging");
        ConfigReader.loadProperties(environment);

        log.info("Environment = {}", environment);

        String url = ConfigReader.get("base.url.contactlist");

        log.info("URL = {}", url);
        RestAssured.baseURI = url;

        Map<String, String> env = new LinkedHashMap<>();

        env.put("Environment", environment);
        env.put("Base URL", url);

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

        ApiAllureUtil.writeAllureEnvironment(env);
        ApiAllureUtil.writeAllureExecutor();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestResult result) {


        log.info("TEST THREAD = {} | TEST CLASS = {}", Thread.currentThread().getName(), getClass().getName());
        log.info("========== START {} ==========", result.getMethod().getMethodName());

        log.info("Executing Test : {}", result.getMethod().getMethodName());
        ApiAllureUtil.attachText("Executing Test:", result.getMethod().getMethodName());

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {



            switch (result.getStatus()) {


                case ITestResult.SUCCESS -> {
                    log.info("PASSED : {}", result.getMethod().getMethodName());
                    ApiAllureUtil.attachText("PASSED:", result.getMethod().getMethodName());
                }

                case ITestResult.FAILURE -> {
                    log.error("FAILED : {}", result.getMethod().getMethodName(),
                            result.getThrowable());
                    ApiAllureUtil.attachText("FAILED:", result.getMethod().getMethodName());
                    ApiAllureUtil.attachException(result.getThrowable());
                }

                case ITestResult.SKIP -> {
                    log.warn("SKIPPED : {}", result.getMethod().getMethodName());
                    ApiAllureUtil.attachText("FAILED:", result.getMethod().getMethodName());

                }

            }

        }


    @AfterSuite(alwaysRun = true)
    public void afterSuite() {

        log.info("======================================================+");
        log.info("FRAMEWORK EXECUTION FINISHED");
        log.info("======================================================+");

    }

}