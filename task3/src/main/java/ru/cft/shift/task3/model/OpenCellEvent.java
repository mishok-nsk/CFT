package ru.cft.shift.task3.model;

public class OpenCellEvent extends GameEvent {
    private final int value;
    private final int x;
    private final int y;

    public OpenCellEvent(int x, int y, int value) {
        super(GameEvent.OPEN_CELL);
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

}
