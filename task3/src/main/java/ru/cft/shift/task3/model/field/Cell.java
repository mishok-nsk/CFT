package ru.cft.shift.task3.model.field;

abstract class Cell {
    int value;

    protected Cell(int value) {
        this.value = value;
    }

    abstract boolean isCanOpen();

    abstract boolean isCanMark();

    abstract Cell openCell();

    abstract Cell markCell();
}
