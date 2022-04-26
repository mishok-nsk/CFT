package ru.cft.shift.task3.view;

import ru.cft.shift.task3.app.GameType;

public interface TimerEventListener {
    void endWinGame(int time, GameType gameType);
}
