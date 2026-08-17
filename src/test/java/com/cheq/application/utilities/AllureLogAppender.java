package com.cheq.application.utilities;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class AllureLogAppender extends AppenderBase<ILoggingEvent> {

    private static final ThreadLocal<StringBuilder> TEST_LOGS =
            new ThreadLocal<>();

    public static void startCapture() {
        TEST_LOGS.set(new StringBuilder());
    }

    public static String stopCapture() {

        StringBuilder logs = TEST_LOGS.get();

        TEST_LOGS.remove();

        if (logs == null || logs.isEmpty()) {
            return "No logs captured.";
        }

        return logs.toString();
    }

    @Override
    protected void append(ILoggingEvent event) {

        StringBuilder logs = TEST_LOGS.get();

        if (logs != null) {

            logs.append(event.getFormattedMessage())
                    .append(System.lineSeparator());
        }
    }
}