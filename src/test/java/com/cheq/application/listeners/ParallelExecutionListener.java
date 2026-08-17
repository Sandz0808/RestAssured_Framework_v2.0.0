package com.cheq.application.listeners;

import com.cheq.application.utilities.ConfigReader;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

public class ParallelExecutionListener implements IAlterSuiteListener {

    @Override
    public void alter(List<XmlSuite> suites) {

        boolean parallelEnabled = Boolean.parseBoolean(
                ConfigReader.get("parallel.enabled")
        );

        int threadCount = parallelEnabled
                ? Integer.parseInt(ConfigReader.get("thread.count"))
                : 1;

        System.out.println(
                "Parallel Execution: " + parallelEnabled +
                        " | Thread Count: " + threadCount
        );

        for (XmlSuite suite : suites) {

            if (parallelEnabled) {

                suite.setParallel(XmlSuite.ParallelMode.CLASSES);
                suite.setThreadCount(threadCount);

            } else {

                suite.setParallel(XmlSuite.ParallelMode.NONE);
                suite.setThreadCount(1);
            }
        }
    }
}