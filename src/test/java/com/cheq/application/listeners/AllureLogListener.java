package com.cheq.application.listeners;

import com.cheq.application.utilities.AllureLogAppender;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class AllureLogListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(
            IInvokedMethod method,
            ITestResult testResult) {

        if (method.isTestMethod()) {
            AllureLogAppender.startCapture();
        }
    }

    @Override
    public void afterInvocation(
            IInvokedMethod method,
            ITestResult testResult) {

        if (method.isTestMethod()) {

            String logs = AllureLogAppender.stopCapture();

            Allure.addAttachment(
                    "Execution Logs",
                    "text/plain",
                    logs,
                    ".log");
        }
    }
}