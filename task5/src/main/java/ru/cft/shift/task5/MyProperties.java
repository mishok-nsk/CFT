package ru.cft.shift.task5;

import java.io.FileInputStream;
import java.util.Properties;

public class MyProperties {
    private static final String PATH_TO_PROPERTIES = "task5/src/main/resources/config.properties";
    private static final String DEFAULT_TIME = "1";
    private static final String DEFAULT_COUNT = "1";
    private static final String DEFAULT_SIZE = "10";

    private int producerCount;
    private int consumerCount;
    private int producerTime;
    private int consumerTime;
    private int storageSize;

    public void readProperties() throws Exception {
        Properties prop = new Properties();
        try (FileInputStream stream = new FileInputStream(PATH_TO_PROPERTIES)) {
            prop.load(stream);
            producerCount = Integer.parseInt(prop.getProperty("producerCount", DEFAULT_COUNT));
            producerTime = Integer.parseInt(prop.getProperty("producerTime", DEFAULT_TIME));
            consumerCount = Integer.parseInt(prop.getProperty("consumerCount", DEFAULT_COUNT));
            consumerTime = Integer.parseInt(prop.getProperty("consumerTime", DEFAULT_TIME));
            storageSize = Integer.parseInt(prop.getProperty("storageSize", DEFAULT_SIZE));
        }
    }

    public int getProducerCount() {
        return producerCount;
    }

    public int getConsumerCount() {
        return consumerCount;
    }

    public int getProducerTime() {
        return producerTime;
    }

    public int getConsumerTime() {
        return consumerTime;
    }

    public int getStorageSize() {
        return storageSize;
    }
}
