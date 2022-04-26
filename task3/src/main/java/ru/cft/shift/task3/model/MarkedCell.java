package ru.cft.shift.task3.model;

class MarkedCell extends Cell {
    // private final GameModel gameModel;

    MarkedCell(int value) {
        super(value);
        // this.gameModel = gameModel;
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
