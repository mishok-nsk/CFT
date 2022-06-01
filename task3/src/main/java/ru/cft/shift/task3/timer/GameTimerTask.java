package ru.cft.shift.task3.timer;

import java.util.TimerTask;

public class GameTimerTask extends TimerTask {
    GameTimer timer;

    public GameTimerTask(GameTimer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        timer.incrementTime();
    }
}
