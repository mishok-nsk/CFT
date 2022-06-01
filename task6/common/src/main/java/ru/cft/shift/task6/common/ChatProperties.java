package ru.cft.shift.task6.common;

import java.io.FileInputStream;
import java.util.Properties;

public class ChatProperties {
    private static final String PROPERTIES_FILE = "config.properties";
    private static final String DEFAULT_PORT = "9012";

    public static int readPort() {
        Properties prop = new Properties();
        try (FileInputStream stream = new FileInputStream(ClassLoader.getSystemResource(PROPERTIES_FILE).getFile())) {
            prop.load(stream);
            int port = Integer.parseInt(prop.getProperty("port", DEFAULT_PORT));
            return port;
        } catch (Exception e) {
            return Integer.parseInt(DEFAULT_PORT);
        }
    }
}
