package ru.cft.shift.task3.model.field;

class CellContext {
    Cell cellState;

    public CellContext() {
        cellState = new ClosedCell(0);
    }

    public boolean openCell() {
        if (cellState.isCanOpen()) {
            cellState = cellState.openCell();
            return true;
        }
        return false;
    }

    public boolean markedCell() {
        if (cellState.isCanMark()) {
            cellState = cellState.markCell();
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
