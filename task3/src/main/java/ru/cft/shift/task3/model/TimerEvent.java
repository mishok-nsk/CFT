package ru.cft.shift.task3.model;

public class TimerEvent extends GameEvent {
    int time;

    public TimerEvent(int time) {
        super(SET_TIME);
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
