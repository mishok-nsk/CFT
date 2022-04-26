package ru.cft.shift.task3.model;

class OpenCell extends Cell {
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
