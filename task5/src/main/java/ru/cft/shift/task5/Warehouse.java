package ru.cft.shift.task5;

import java.util.ArrayDeque;
import java.util.Queue;

public class Warehouse {
    private final int storageSize;
    private int productCount;
    private Queue<Integer> products;

    public Warehouse(int storageSize) {
        this.storageSize = storageSize;
        products = new ArrayDeque<>(storageSize);
    }

    public boolean isFull() {
        return products.size() == storageSize;
    }

    public void putInStock(int idProduct) throws WarehouseException {
        if (isFull()) {
            throw new WarehouseException("Warehouse is full!");
        }
        products.add(idProduct);
        // productCount++;
    }

    public boolean isEmpty() {
        return products.size() == 0;
    }

    public int getFromStock() throws WarehouseException {
        if (isEmpty()) {
            throw new WarehouseException("Warehouse is empty!");
        }
        return products.remove();
    }
}
