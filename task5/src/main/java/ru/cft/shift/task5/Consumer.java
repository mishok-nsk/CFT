package ru.cft.shift.task5;

public class Consumer extends Thread {
    private static final Object SYNC_CONS = new Object();
    private static Warehouse warehouse;
    private final int time;

    public static void setWarehouse(Warehouse inWarehouse) {
        warehouse = inWarehouse;
    }

    public Consumer(int time) {
        this.time = time;
    }

    public void run() {
        try {
            synchronized (SYNC_CONS) {
                synchronized (warehouse) {
                    while (warehouse.isEmpty()) {
                        wait();
                    }

                }
                sleep(time);
                warehouse.getFromStock();
                notify();
            }
        } catch (InterruptedException e) {

        } catch (WarehouseException e) {

        }
    }
}
