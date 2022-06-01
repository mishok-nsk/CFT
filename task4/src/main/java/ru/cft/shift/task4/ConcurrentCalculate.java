package ru.cft.shift.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ConcurrentCalculate {
    public static final int THREAD_NUMBER = Runtime.getRuntime().availableProcessors() - 1;
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentCalculate.class);

    private final int number;

    public ConcurrentCalculate(int number) {
        this.number = number;
    }

    public void calculate() {
        List<Future<Double>> tasks = new ArrayList<>(THREAD_NUMBER - 1);
        int numberPerTask = number / THREAD_NUMBER;
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            int start = numberPerTask * (i -1) + 1;
            int end = i == THREAD_NUMBER ? number : numberPerTask * i;
            tasks.add(CompletableFuture.supplyAsync(new ThreadTask(start, end)::calculate));
        }

        double sum = tasks.stream()
                .mapToDouble(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        logger.error("Exception method get", e);
                        throw new IllegalStateException();
                    }
                })
                .sum();
        logger.info("Сумма значений {} элементов равна {} ", number, sum);
    }
}
