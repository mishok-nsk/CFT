package ru.cft.shift.task3.model;

class CloseCell extends Cell {
    // private final GameModel gameModel;

    CloseCell(int value) {
        super(value);
        // this.gameModel = gameModel;
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
