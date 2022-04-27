package ru.cft.shift.task3.model;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    GameModel model;

    public MyTimerTask(GameModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        model.incrementTime();
    }
}
