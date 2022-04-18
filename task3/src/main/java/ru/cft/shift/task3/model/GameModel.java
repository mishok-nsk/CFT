package ru.cft.shift.task3.model;


import java.util.Iterator;
import java.util.Random;

public class GameModel {
    public static final int BOMB = 9;
    public static final int EMPTY = 0;
    private final int row;
    private final int col;
    private final int mineNumber;
    private int mineLeft;
    private boolean isFieldFilled = false;
    // private int[][] cells;
    private CellContext[][] cells;
    private OpenCellListener openCellListener;
    private FlagCellListener flagCellListener;
    private NewGameListener newGameListener;

    public GameModel(int row, int col, int mineNumber) {
        this.row = row;
        this.col = col;
        this.mineNumber = mineNumber;
        mineLeft = mineNumber;
        // cells = new int[row][col];
        cells = new CellContext[row][col];
    }

    public void setOpenCellListener(OpenCellListener listener) {
        openCellListener = listener;
    }

    public void setFlagCellListener(FlagCellListener listener) {
        flagCellListener = listener;
    }

    public void setNewGameListener(NewGameListener listener) {
        newGameListener = listener;
    }

    public void newGame() {
        newGameListener.startNewGame(row, col);
    }

    public void openCell(int x, int y) {
        if (!isFieldFilled) {
            fillField(x, y);
        }
        if (cells[y][x].openCell()) {
            int value = cells[y][x].getValue();
            openCellListener.cellOpen(x, y, value);
            if (value == BOMB) openAll();
            if (value == EMPTY) openNeighbours(x, y);
        }
    }

    public void flaggingCell(int x, int y) {
        if (cells[y][x].flaggingCell()) {
            flagCellListener.cellFlagging(x, y);
        }
    }

    private void fillField(int xInit, int yInit) {
        isFieldFilled = true;
        initCells();
        Random rand = new Random();

        for (int i = 0; i < mineNumber; i++) {
            int x, y;
            y = rand.nextInt(row);
            x = rand.nextInt(col);
            cells[y][x].setValue(BOMB);
            incrementNeighbours(x, y);
        }
    }

    private void initCells() {
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                cells[y][x] = new CellContext();
            }
        }
    }

    private void incrementNeighbours(int x, int y) {
        Iterator<Coordinate> it = neighbours(x, y);
        while(it.hasNext()) {
            Coordinate coord = it.next();
            if (cells[coord.x][coord.y].getValue() != BOMB) {
                cells[coord.y][coord.x].incrementValue();
            }
        }
    }
    private void openAll() {
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                openCell(x, y);
            }
        }
    }

    private void openNeighbours(int x, int y) {
        Iterator<Coordinate> it = neighbours(x, y);
        while(it.hasNext()) {
            Coordinate coord = it.next();
            openCell(coord.x, coord.y);
        }
    }

    private Iterator<Coordinate> neighbours(int x, int y) {
        return new Iterator<Coordinate>() {
            int step;

            @Override
            public boolean hasNext() {
                return (step < 9);
            }

            @Override
            public Coordinate next() {
                Coordinate coord = new Coordinate();
                coord.x = step % 3 + (x - 1);
                coord.y = step / 3 + (y - 1);
                step++;
                if (step == 5) { // пропускаем ячейку с координатами равными исходным
                    return next();
                }
                return coord;
            }
        };
    }

    class Coordinate {
        int x;
        int y;
    }

    abstract class Cell {
        int value;
        protected Cell(int value) {
            this.value = value;
        }
        abstract boolean isCanOpened();
        abstract boolean isCanFlagged();
        abstract Cell openCell();
        abstract Cell flaggingCell();
    }

    class CloseCell extends Cell {
        CloseCell(int value) {
            super(value);
        }

        @Override
        boolean isCanOpened() {
            return true;
        }

        @Override
        boolean isCanFlagged() {
            return true;
        }

        @Override
        Cell openCell() {
            return new OpenCell(value);
        }

        @Override
        Cell flaggingCell() {
            mineLeft--;
            return new FlaggedCell(value);
        }
    }

    class OpenCell extends Cell {
        OpenCell(int value) {
            super(value);
        }

        @Override
        boolean isCanOpened() {
            return false;
        }

        @Override
        boolean isCanFlagged() {
            return false;
        }

        Cell openCell() {
            return this;
        }

        Cell flaggingCell() {
            return this;
        }
    }

    class FlaggedCell extends Cell {
        FlaggedCell(int value) {
            super(value);
        }

        @Override
        boolean isCanOpened() {
            return false;
        }

        @Override
        boolean isCanFlagged() {
            return true;
        }

        Cell openCell() {
            return this;
        }

        Cell flaggingCell() {
            mineLeft++;
            return new CloseCell(value);
        }
    }

    class CellContext {
        Cell cellState;

        public CellContext() {
            cellState = new CloseCell(0);
        }

        public boolean openCell() {
            if (cellState.isCanOpened()) {
                cellState = cellState.openCell();
                return true;
            }
            return false;
        }

        public boolean flaggingCell() {
            if (cellState.isCanFlagged()) {
                cellState  = cellState.flaggingCell();
                return true;
            }
            return false;
        }

        public void setValue(int value) {
            cellState.value = value;
        }

        public void incrementValue() {
            cellState.value++;
        }

        public int getValue() {
            return cellState.value;
        }
    }

}
