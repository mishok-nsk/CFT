package ru.cft.shift.task3.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyProperties {
    public static final String PATH_TO_PROPERTIES = "task3/src/main/resources/config.properties";
    private static final Logger logger = LoggerFactory.getLogger(MyProperties.class);
    private static final String DEFAULT_HIGH_SCORE_FILE_NAME = "record.info";

    public static Properties get() {
        Properties prop = new Properties();
        try (FileInputStream stream = new FileInputStream(PATH_TO_PROPERTIES)) {
            logger.info("Читаем .properties.");
            prop.load(stream);
        } catch (IOException e) {
            logger.error("Не удалось прочитать .properties: ", e);
            prop.put("highScoreFileName", DEFAULT_HIGH_SCORE_FILE_NAME);
        }
        return prop;
    }
}
