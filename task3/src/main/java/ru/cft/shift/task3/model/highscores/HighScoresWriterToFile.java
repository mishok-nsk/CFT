package ru.cft.shift.task3.model.highscores;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HighScoresWriterToFile implements HighScoresWriter {
    private static final Logger logger = LoggerFactory.getLogger(HighScoresWriterToFile.class);
    private final String fileName;
    private final ObjectMapper mapper;

    public HighScoresWriterToFile(String fileName, ObjectMapper mapper) {
        this.fileName = fileName;
        this.mapper = mapper;
    }

    public void writeHighScores(Map<String, HighScoreData> highScores) {
        try (OutputStreamWriter sw = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)) {
            logger.info("Пишем данные рекордов в файл.");
            mapper.writeValue(sw, highScores);
        } catch (Exception e) {
            logger.error("Не удалось записать рекорды в файл: ", e);
        }
    }
}
