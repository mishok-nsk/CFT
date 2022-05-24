package ru.cft.shift.task5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

public class MyProperties {
    private static final Logger logger = LoggerFactory.getLogger(MyProperties.class);
    private static final String FILE_PROPERTIES = "config.properties";
    private static final String DEFAULT_TIME = "1";
    private static final String DEFAULT_COUNT = "1";
    private static final String DEFAULT_SIZE = "10";

    private int producerCount;
    private int consumerCount;
    private int producerTime;
    private int consumerTime;
    private int storageSize;

    public void readProperties() {
        Properties prop = new Properties();
        try (FileInputStream stream = new FileInputStream(ClassLoader.getSystemResource(FILE_PROPERTIES).getFile())) {
            prop.load(stream);
        } catch (Exception e) {
            logger.error("Не удалось прочитать файл .properties", e);
            logger.info("Приложение будет запущено со значениями по умолчанию.");
        } finally {
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
