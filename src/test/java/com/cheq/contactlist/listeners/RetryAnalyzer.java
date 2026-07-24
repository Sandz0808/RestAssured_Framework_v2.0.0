package com.cheq.contactlist.listeners;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import  com.cheq.contactlist.utils.LoggerUtil;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log =
            LoggerUtil.getLogger(RetryAnalyzer.class);

    private static final int MAX_RETRY_COUNT = 1;

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {

        Throwable throwable = result.getThrowable();

        Response response =
                (Response) result.getAttribute("responses");

        boolean retryable =
                RetryPolicy.shouldRetry(throwable)
                        || RetryPolicy.shouldRetry(response);

        if (!retryable) {

            log.info(
                    "Retry skipped. Failure is not retryable."
            );

            return false;
        }

        if (retryCount < MAX_RETRY_COUNT) {

            retryCount++;

            log.warn(
                    "Retry {} of {} for [{}]",
                    retryCount,
                    MAX_RETRY_COUNT,
                    result.getMethod().getMethodName()
            );

            return true;
        }

        log.error(
                "Maximum retry reached for [{}]",
                result.getMethod().getMethodName()
        );

        return false;
    }
}