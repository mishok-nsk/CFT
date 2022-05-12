package ru.cft.shift.task3.model.highscores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public record HighScoresReaderFromFile(String fileName,
                                       ObjectMapper mapper) implements HighScoresReader {
    public static final Logger logger = LoggerFactory.getLogger(HighScoresReaderFromFile.class);

    @Override
    public void readHighScores(Map<String, HighScoreData> highScoreDataMap) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                new File(file.getParent()).mkdir();
                file.createNewFile();
            }
            highScoreDataMap.putAll(mapper.readValue(file, new TypeReference<HashMap<String, HighScoreData>>() {
            }));
        } catch (IOException e) {
            logger.error("Не удалось прочитать данные рекордов из файла.", e);
        }
    }
}
