package ru.cft.shift.task3.model.highscores;

import java.util.Map;

public interface HighScoresWriter {
    void writeHighScores(Map<String, HighScoreData> highScores);
}
