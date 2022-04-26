package ru.cft.shift.task3.model;

class CellContext {
    Cell cellState;

    public CellContext() {
        cellState = new CloseCell(0);
    }

    public boolean openCell() {
        if (cellState.isCanOpened()) {
            cellState = cellState.openCell();
            return true;
        }
        return false;
    }

    public boolean markedCell() {
        if (cellState.isCanMarked()) {
            cellState = cellState.markedCell();
            return true;
        }
        return false;
    }

    public void setValue(int value) {
        cellState.value = value;
    }

    public void incrementValue() {
        cellState.value++;
    }

    public int getValue() {
        return cellState.value;
    }
}
