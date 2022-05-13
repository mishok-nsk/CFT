package ru.cft.shift.task3.model.highscores;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.app.GameType;

import java.util.HashMap;
import java.util.Map;

public class HighScoresHandler implements HighScoreChecker {
    private static final Logger logger = LoggerFactory.getLogger(HighScoresHandler.class);
    public final String fileName;
    private static final Map<String, HighScoreData> highScoreDataMap = new HashMap<>(3);
    private int highScoreTime;
    private String gameType;
    private HighScoreListener highScorelistener;
    private UpdateRecordListener updateRecordListener;
    private final ObjectMapper objectMapper;

    public HighScoresHandler(String fileName) {
        this.fileName = fileName;
        objectMapper = new ObjectMapper();
    }

    public void setHighScoreListener(HighScoreListener highScorelistener) {
        this.highScorelistener = highScorelistener;
    }

    public void setUpdateRecordListener(UpdateRecordListener updateRecordListener) {
        this.updateRecordListener = updateRecordListener;
    }

    @Override
    public void checkTimeRecord(int time, GameType gameType) {
        logger.info("Проверка времени завершения игры на рекорд.");
        HighScoreData highScore = highScoreDataMap.get(gameType.getName());
        if (highScore == null || highScore.getTimeValue() > time) {
            highScoreTime = time;
            this.gameType = gameType.getName();
            updateRecordListener.getUserName();
        }
    }

    public void updateHighScore(String name) {
        logger.info("Обновляем данные рекордов.");
        if (name.isBlank()) {
            name = "Unknown";
        }
        HighScoreData hsd = new HighScoreData(name, highScoreTime);
        highScoreDataMap.put(gameType, hsd);
        highScorelistener.setHighScore(gameType, name, highScoreTime);
    }

    public void writeHighScore() {
        logger.info("Записываем данные рекордов.");
        HighScoresWriter hsw = new HighScoresWriterToFile(fileName, objectMapper);
        hsw.writeHighScores(highScoreDataMap);
    }

    public void readHighScoreData() {
        logger.info("Читаем данные рекордов.");
        HighScoresReader hsr = new HighScoresReaderFromFile(fileName, objectMapper);
        hsr.readHighScores(highScoreDataMap);
        for (String gameType : highScoreDataMap.keySet()) {
            highScorelistener.setHighScore(gameType, highScoreDataMap.get(gameType).getWinnerName(), highScoreDataMap.get(gameType).getTimeValue());
        }
    }
}
