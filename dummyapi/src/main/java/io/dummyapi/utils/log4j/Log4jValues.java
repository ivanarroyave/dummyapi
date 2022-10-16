package io.dummyapi.utils.log4j;

import static io.dummyapi.utils.so.AccordingToOperatingSystem.replaceLineSeparatorOfPath;

public enum Log4jValues {
    LOG4J_PROPERTIES_FILE_PATH(System.getProperty("user.dir") + "\\src\\main\\resources\\log4j2.properties");

    private final String value;

    Log4jValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return replaceLineSeparatorOfPath(value, "\\");
    }
}
