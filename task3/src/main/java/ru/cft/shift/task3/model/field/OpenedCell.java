package ru.cft.shift.task3.model.field;

class OpenedCell extends Cell {
    OpenedCell(int value) {
        super(value);
    }

    @Override
    boolean isCanOpen() {
        return false;
    }

    @Override
    boolean isCanMark() {
        return false;
    }

    Cell openCell() {
        return this;
    }

    Cell markCell() {
        return this;
    }
}
