package ru.cft.shift.task3.model.field;

class ClosedCell extends Cell {

    ClosedCell(int value) {
        super(value);
    }

    @Override
    boolean isCanOpen() {
        return true;
    }

    @Override
    boolean isCanMark() {
        return true;
    }

    @Override
    Cell openCell() {
        return new OpenedCell(value);
    }

    @Override
    Cell markCell() {
        return new MarkedCell(value);
    }
}
