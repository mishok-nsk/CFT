package ru.cft.shift.task3.timer;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    MyTimer timer;

    public MyTimerTask(MyTimer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        timer.incrementTime();
    }
}
