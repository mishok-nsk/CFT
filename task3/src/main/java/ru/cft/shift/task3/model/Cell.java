package ru.cft.shift.task3.model;

abstract class Cell {
    int value;

    protected Cell(int value) {
        this.value = value;
    }

    abstract boolean isCanOpened();

    abstract boolean isCanMarked();

    abstract Cell openCell();

    abstract Cell markedCell();
}
