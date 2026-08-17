package com.cheq.application.listeners;

import com.cheq.application.utilities.ConfigReader;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

public class DynamicThreadCountListener implements IAlterSuiteListener {

    @Override
    public void alter(List<XmlSuite> suites) {

        String environment =
                System.getProperty("config", "staging");

        ConfigReader.loadProperties(environment);

        String threadCount =
                ConfigReader.get("thread.count");

        int workers = Integer.parseInt(threadCount);

        for (XmlSuite suite : suites) {
            suite.setThreadCount(workers);
        }

        System.out.println(
                "Environment = " + environment +
                        " | Thread Count = " + workers
        );
    }
}