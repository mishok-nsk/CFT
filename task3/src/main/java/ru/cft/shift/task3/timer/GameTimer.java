package ru.cft.shift.task3.timer;

import java.util.Timer;

public class GameTimer {
    private Timer timer;
    private int time;
    private TimerListener timerListener;

    public void setTimerListener(TimerListener listener) {
        timerListener = listener;
    }
    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameTimerTask(this), 0, 1000);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void reset() {
        stop();
        time = 0;
        timerListener.setTime(time);
    }

    public void incrementTime() {
        timerListener.setTime(++time);
    }

    public int getTime() {
        return time;
    }
}
