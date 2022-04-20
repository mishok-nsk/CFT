package ru.cft.shift.task3.model;

public class GameEvent {
    public static final int NEW_GAME = 0;
    public static final int START_GAME = 1;
    public static final int OPEN_CELL = 2;
    public static final int MARKED_CELL = 3;
    public static final int UNMARKED_CELL = 4;
    public static final int YOU_WIN = 5;
    public static final int YOU_LOSE = 6;

    private final int event;

    public GameEvent(int event) {
        this.event = event;
    }

    public int getEvent() {
        return event;
    }

}
