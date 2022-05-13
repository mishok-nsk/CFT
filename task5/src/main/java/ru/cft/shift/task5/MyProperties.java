package ru.cft.shift.task5;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    public static final String PATH_TO_PROPERTIES = "task5/src/main/resources/config.properties";

    private int producerCount;
    private int consumerCount;
    private int producerTime;
    private int consumerTime;
    private int storageSize;

    public void readProperties() {
        Properties prop = new Properties();
        try (FileInputStream stream = new FileInputStream(PATH_TO_PROPERTIES)) {
            // logger.info("Читаем .properties.");
            prop.load(stream);
            producerCount = (int) prop.get("producerCount");
            producerTime = (int) prop.get("producerTime");
            consumerCount = (int) prop.get("consumerCount");
            consumerTime = (int) prop.get("consumerTime");
            storageSize = (int) prop.get("storageSize");
        } catch (IOException e) {
            // logger.error("Не удалось прочитать файл .properties: ", e);
            // prop.put("highScoreFileName", DEFAULT_HIGH_SCORE_FILE_NAME);
        } catch (Exception e) {

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
