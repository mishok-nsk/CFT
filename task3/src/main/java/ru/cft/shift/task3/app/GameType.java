package ru.cft.shift.task3.app;

public enum GameType {
    NOVICE("Novice", 10, 9, 9),
    MEDIUM("Medium", 40, 16, 16),
    EXPERT("Expert", 99, 16, 30);

    private final String name;
    private final int bombCount;
    private final int row;
    private final int col;
    GameType(String name, int bombCount, int row, int col) {
        this.name = name;
        this.bombCount = bombCount;
        this.row = row;
        this.col = col;
    }

    public String getName() {
        return name;
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
