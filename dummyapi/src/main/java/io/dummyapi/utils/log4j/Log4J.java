package io.dummyapi.utils.log4j;

import org.apache.log4j.PropertyConfigurator;

public class Log4J {
    private Log4J() {
    }

    public static void setUpLog4j2(String log4jPropertiesPath){
        PropertyConfigurator.configure(log4jPropertiesPath);
    }
}
