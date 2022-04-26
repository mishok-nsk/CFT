package ru.cft.shift.task3.model;

import java.util.Map;

public interface HighScoresWriter {
    void writeHighScores(Map<String, HighScoreData> highScores);
}
