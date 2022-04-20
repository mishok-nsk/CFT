package ru.cft.shift.task3.view;

public enum GameType {
    NOVICE(10, 9, 9),
    MEDIUM(40, 16, 16),
    EXPERT(99, 16, 30);

    private final int bombCount;
    private final int row;
    private final int col;
    GameType(int bombCount, int row, int col) {
        this.bombCount = bombCount;
        this.row = row;
        this.col = col;
    }

    public int getBombCount() {
        return bombCount;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
