package ru.cft.shift.task3.model.highscores;

public interface HighScoreListener {
    void setHighScore(String gameType, String name, int time);
}
