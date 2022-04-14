package ru.cft.shift.task3.model;

public class GameModel {
    final int row;
    final int col;
    int[][] cells;
    OpenCellListener openCellListener;
    NewGameListener newGameListener;

    public GameModel(int row, int col) {
        this.row = row;
        this.col = col;
        cells = new int[row][col];
    }

    public void setOpenCellListener(OpenCellListener cellListener) {
        openCellListener = cellListener;
    }

    public void setNewGameListener(NewGameListener listener) {
        newGameListener = listener;
    }

    public void newGame() {
        newGameListener.startNewGame(row, col);
    }

    public void openCell(int x, int y) {
        openCellListener.cellOpen(x, y, cells[x][y]);
    }
}
