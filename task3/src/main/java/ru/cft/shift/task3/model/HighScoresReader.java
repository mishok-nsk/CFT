package ru.cft.shift.task3.model;

import java.util.Map;

public interface HighScoresReader {
    void readHighScores(Map<String, HighScoreData> highScoresDataMap);
}
