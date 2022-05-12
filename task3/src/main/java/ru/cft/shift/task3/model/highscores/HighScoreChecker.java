package ru.cft.shift.task3.model.highscores;

import ru.cft.shift.task3.app.GameType;

public interface HighScoreChecker {
    void checkTimeRecord(int time, GameType gameType);
}
