package ru.cft.shift.task3.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HighScoresReaderFromFile implements HighScoresReader {
    public static final Logger logger = LoggerFactory.getLogger(HighScoresReaderFromFile.class);
    private final String fileName;

    public HighScoresReaderFromFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void readHighScores(Map<String, HighScoreData> highScoreDataMap) {
        try {
        File file = new File(fileName);
        ObjectMapper mapper = new ObjectMapper();
        highScoreDataMap.putAll(mapper.readValue(file, new TypeReference<HashMap<String, HighScoreData>>() {}));
        } catch (IOException e) {
            logger.error("Не удалось прочитать данные рекордов из файла.", e);
        }
    }
}
