package ru.cft.shift.task3.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.util.Map;

public class HighScoresWriterToFile implements HighScoresWriter {
    private static final Logger logger = LoggerFactory.getLogger(HighScoresWriterToFile.class);
    private final String fileName;

    public HighScoresWriterToFile(String fileName) {
        this.fileName = fileName;
    }

    public void writeHighScores(Map<String, HighScoreData> highScores) {
        try (FileWriter fw = new FileWriter(fileName)) {
            logger.info("Пишем данные рекордов в файл.");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(fw, highScores);
        } catch (Exception e) {
            logger.error("Не удалось записать рекорды в файл: ", e);
        }
    }
}
