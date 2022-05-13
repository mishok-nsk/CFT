package ru.cft.shift.task5;

public class Producer extends Thread {
    private static final Object SYNC_PROD = new Object();
    private static Warehouse warehouse;
    private static int idProduct;
    private final int time;

    public static void setWarehouse(Warehouse inWarehouse) {
        warehouse = inWarehouse;
    }
    public Producer(int time) {
        this.time = time;
    }

    public void run() {
        try {
            synchronized (SYNC_PROD) {
                synchronized (warehouse) {
                    while (warehouse.isFull()) {
                          wait();
                    }

                }
                sleep(time);
                warehouse.putInStock(idProduct++);
                notify();
            }
        } catch (InterruptedException e) {

        } catch (WarehouseException e) {

        }
    }
}
