package ru.cft.shift.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.*;


public class ConcurrentCalculate {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentCalculate.class);
    public static final int THREAD_NUMBER = 4;

    static Callable<Double> task(int numberOfThread, int end) {
        return () -> {
            double result = 0;
            for (int i = numberOfThread; i <= end; i += THREAD_NUMBER) {
                logger.debug("Поток {} просчитал значение для {} элемента", Thread.currentThread().getName(), i);
                result += 1.0 / i / (i + 1); // функция 1 / n(n+1). Умножение в знаменателе заменено на два деления чтобы не допустить переполнения.
            }
            logger.info("Поток {} просчитал значение элементов. Результат {}", Thread.currentThread().getName(), result);
            return result;
        };
    }

    public static void main(String[] args) {
        int number;
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Введите количество элементов вычисления:");
            String input = in.nextLine();
            number = Integer.parseInt(input);
            if (number < 1) {
                logger.info("Введенное некорректное число.");
                return;
            }
        } catch (NoSuchElementException e) {
            logger.error("Данные не введены.");
            return;
        } catch (NumberFormatException e) {
            logger.error("Введены некорректные данные.");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
        List<Callable<Double>> tasks = new ArrayList<>(THREAD_NUMBER);
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            tasks.add(task(i, number));
        }

        double sum = 0;
        try {
            sum = executor.invokeAll(tasks).stream()
                    .mapToDouble(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            logger.error("Exception method get", e);
                            throw new IllegalStateException();
                        }
                    })
                    .sum();
        } catch (InterruptedException e) {
            logger.error("Thread is interrupted. ", e);
        }
        executor.shutdown();
        logger.info("Сумма значений {} элементов равна {} ", number, sum);
        }
}
