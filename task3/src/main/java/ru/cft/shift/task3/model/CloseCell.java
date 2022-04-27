package ru.cft.shift.task3.model;

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
