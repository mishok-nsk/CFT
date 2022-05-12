package ru.cft.shift.task3.model.field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task3.model.GameModel;
import ru.cft.shift.task3.model.gamelistener.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {
    private static final Logger logger = LoggerFactory.getLogger(Field.class);
    public static final int BOMB = 9;
    public static final int EMPTY = 0;

    private final int row;
    private final int col;
    private final int bomb;
    private final CellContext[][] cells;
    private int mineLeft;
    private int cellsClosed;
    private OpenCellListener openCellListener;
    private MarkCellListener markCellListener;
    private final GameModel owner;

    public Field(int row, int col, int bomb, GameModel owner) {
        this.row = row;
        this.col = col;
        this.bomb = bomb;
        this.owner = owner;
        cells = new CellContext[row][col];
        initCells();
    }

    public void setOpenCellListener(OpenCellListener listener) {
        openCellListener = listener;
    }

    public void setMarkedCellListener(MarkCellListener listener) {
        markCellListener = listener;
    }

    public void initCells() {
        logger.info("инициализируем игровое поле.");
        mineLeft = bomb;
        cellsClosed = row * col;
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                cells[y][x] = new CellContext();
            }
        }
    }

    public void openCells(int x, int y) {
        openCell(x, y);
        if (cellsClosed == bomb) {
            owner.endGame(true);
        }
    }

    private void openCell(int x, int y) {
        if (cells[y][x].openCell()) {
            int value = getCellValue(x, y);
            if (value == BOMB) {
                openAll();
            }
            if (value == EMPTY) openNeighbours(x, y);
        }
    }

    public void markedCell(int x, int y) {
        if (cells[y][x].markedCell()) {
            boolean isMarked = cells[y][x].cellState.getClass().equals(MarkedCell.class);
            if (isMarked) {
                if (mineLeft == 0) {
                    cells[y][x].markedCell();
                    return;
                }
                markCellListener.markCell(x, y, --mineLeft, true);
                logger.info("Ячейка [{}] [{}] помечена флагом", x, y);
            } else {
                markCellListener.markCell(x, y, ++mineLeft, false);
                logger.info("Флаг с ячейки [{}] [{}] снят", x, y);
            }
        }
    }

    public void openNeighboursWithCheck(int x, int y) {
        if (!cells[y][x].cellState.getClass().equals(OpenedCell.class)) {
            return;
        }
        List<Coordinate> neighbours = getNeighbours(x, y);
        if (cells[y][x].getValue() == neighbours.stream().filter(this::isMarked).count()) {
            neighbours.forEach(this::openCellCoordinate);
        }
        if (cellsClosed == bomb) {
            owner.endGame(true);
        }
    }

    private boolean isMarked(Coordinate coord) {
        return cells[coord.y][coord.x].cellState.getClass().equals(MarkedCell.class);
    }

    private void openNeighbours(int x, int y) {
        getNeighbours(x, y).forEach(this::openCellCoordinate);
    }

    private void openCellCoordinate(Coordinate coord) {
        openCell(coord.x, coord.y);
    }

    private int getCellValue(int x, int y) {
        cellsClosed--;
        int value = cells[y][x].getValue();
        logger.info("Открываем ячейку [{}] [{}] значение {}.", x, y, value);
        openCellListener.openCell(x, y, value);
        return value;
    }

    public void fillField(int xInit, int yInit) {
        logger.info("Заполняем игровое поле.");
        Random rand = new Random();
        int mineCounter = 0;
        while (mineCounter < bomb) {
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

    }

    private void incrementNeighbours(int x, int y) {
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
                getCellValue(x, y);
            }
        }
        owner.endGame(false);
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
}
