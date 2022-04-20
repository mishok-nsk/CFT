package ru.cft.shift.task3.model;

public class MarkedCellEvent extends GameEvent {
    private final int x;
    private final int y;
    private final int bombCount;

    public MarkedCellEvent(int gameEventType, int x, int y, int bombCount) {
        super(gameEventType);
        this.x = x;
        this.y = y;
        this.bombCount = bombCount;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBombCount() {
        return bombCount;
    }
}
