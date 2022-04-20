package ru.cft.shift.task3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.view.GameType;

public class GameModel {
    private static final Logger logger = LoggerFactory.getLogger(GameModel.class);
    public static final int BOMB = 9;
    public static final int EMPTY = 0;
    private final int row;
    private final int col;
    private final int bombCount;
    private final GameType gameType;
    private int mineLeft;
    private int cellsClosed;
    private boolean isFieldFilled;
    private final CellContext[][] cells;
    private GameListener listener;

    public GameModel(GameType gameType) {
        this.gameType = gameType;
        row = gameType.getRow();
        col = gameType.getCol();
        logger.info("инициализируем игровую модель размером {} на {} .", row, col);
        bombCount = gameType.getBombCount();
        cells = new CellContext[row][col];
        initCells();
        // newGame();
    }

    public void setGameListener(GameListener listener) {
        this.listener = listener;
    }

    public void newGame() {
        initCells();
        listener.fireEvent(new NewGameEvent(gameType));
    }

    public void openCells(int x, int y) {
        if (!isFieldFilled) {
            fillField(x, y);
        }
        if (cells[y][x].openCell()) {
            int value = openCell(x, y);
            if (value == BOMB) openAll();
            if (value == EMPTY) openNeighbours(x, y);
        }
        if (cellsClosed == bombCount) {
            listener.fireEvent(new GameEvent(GameEvent.YOU_WIN));
        }
    }

    public void openNeighboursWithCheck(int x, int y) {
        if (!cells[y][x].cellState.getClass().equals(OpenCell.class)) {
            return;
        }
        List<Coordinate> neighbours = getNeighbours(x, y);
        if (cells[y][x].getValue() == neighbours.stream().filter(this::isMarked).count()) {
            neighbours.forEach(this::openCellCoordinate);
        }
    }

    public void flaggingCell(int x, int y) {
        if (cells[y][x].markedCell()) {
            boolean isMarked = cells[y][x].cellState.getClass().equals(MarkedCell.class);
            if (isMarked) {
                listener.fireEvent(new MarkedCellEvent(GameEvent.MARKED_CELL, x, y, --mineLeft));
            } else {
                listener.fireEvent(new MarkedCellEvent(GameEvent.UNMARKED_CELL, x, y, ++mineLeft));
            }
            logger.info("Ячейка [{}] [{}] помечена флагом: {}", x, y, isMarked);
        }
    }

    private boolean isMarked(Coordinate coord) {
        return cells[coord.y][coord.x].cellState.getClass().equals(MarkedCell.class);
    }

    private void openNeighbours(int x, int y) {
        getNeighbours(x, y).forEach(this::openCellCoordinate);
    }

    private void openCellCoordinate(Coordinate coord) {
        openCells(coord.x, coord.y);
    }

    private int openCell(int x, int y) {
        cellsClosed--;
        int value = cells[y][x].getValue();
        logger.info("Открываем ячейку [{}] [{}] значение {}.", x, y, value);
        listener.fireEvent(new OpenCellEvent(x, y, value));
        return value;
    }

    private void fillField(int xInit, int yInit) {
        logger.info("Заполняем игровое поле.");
        isFieldFilled = true;
        Random rand = new Random();
        int mineCounter = 0;
        while (mineCounter < bombCount) {
            int x, y;
            y = rand.nextInt(row);
            x = rand.nextInt(col);
            if (!(x == xInit && y == yInit) && !(cells[y][x].getValue() == BOMB)) {
                mineCounter++;
                cells[y][x].setValue(BOMB);
                logger.info("Ставим мину {} на ячейку [{}] [{}].", mineCounter, x, y);
                incrementNeighbours(x, y);
            }
        }
        listener.fireEvent(new GameEvent(GameEvent.START_GAME));
    }

    private void initCells() {
        isFieldFilled = false;
        mineLeft = bombCount;
        cellsClosed = row * col;
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                cells[y][x] = new CellContext();
            }
        }
    }

    private void incrementNeighbours(int x, int y) {
        // changeNeighbours(x, y, this::incrementNeighbourValue);
        getNeighbours(x, y).forEach(this::incrementNeighbourValue);

    }
    private void incrementNeighbourValue(Coordinate coord) {
        if (cells[coord.y][coord.x].getValue() != BOMB) {
            cells[coord.y][coord.x].incrementValue();
        }
    }

    private void openAll() {
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                openCell(x, y);
            }
        }
        listener.fireEvent(new GameEvent(GameEvent.YOU_LOSE));
    }

    private List<Coordinate> getNeighbours(int xInit, int yInit) {
        List<Coordinate> list = new ArrayList<>(8);
        for(int x = xInit - 1; x < xInit + 2; x++) {
                for (int y = yInit - 1; y < yInit +2; y++) {
                    if (x >= 0 && y >= 0 && x < col && y < row && !(x == xInit && y == yInit)) {
                        list.add(new Coordinate(x, y));
                    }
                }
            }
        return list;
    }

    static class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    abstract static class Cell {
        int value;
        protected Cell(int value) {
            this.value = value;
        }
        abstract boolean isCanOpened();
        abstract boolean isCanMarked();
        abstract Cell openCell();
        abstract Cell markedCell();
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
        boolean isCanMarked() {
            return true;
        }

        @Override
        Cell openCell() {
            return new OpenCell(value);
        }

        @Override
        Cell markedCell() {
            return new MarkedCell(value);
        }
    }

    static class OpenCell extends Cell {
        OpenCell(int value) {
            super(value);
        }

        @Override
        boolean isCanOpened() {
            return false;
        }

        @Override
        boolean isCanMarked() {
            return false;
        }

        Cell openCell() {
            return this;
        }

        Cell markedCell() {
            return this;
        }
    }

    class MarkedCell extends Cell {
        MarkedCell(int value) {
            super(value);
        }

        @Override
        boolean isCanOpened() {
            return false;
        }

        @Override
        boolean isCanMarked() {
            return true;
        }

        Cell openCell() {
            return this;
        }

        Cell markedCell() {
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

        public boolean markedCell() {
            if (cellState.isCanMarked()) {
                cellState  = cellState.markedCell();
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
