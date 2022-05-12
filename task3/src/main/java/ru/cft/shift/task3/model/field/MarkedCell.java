package ru.cft.shift.task3.model.field;

class MarkedCell extends Cell {

    MarkedCell(int value) {
        super(value);
    }

    @Override
    boolean isCanOpen() {
        return false;
    }

    @Override
    boolean isCanMark() {
        return true;
    }

    Cell openCell() {
        return this;
    }

    Cell markCell() {
        return new ClosedCell(value);
    }
}
