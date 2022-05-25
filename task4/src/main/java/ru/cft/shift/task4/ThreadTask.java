package ru.cft.shift.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTask {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentCalculate.class);
    private final int start;
    private final int end;

    public ThreadTask(int start, int end) {
        this.start = start;
        this.end = end;
    }
    public double calculate() {
        double sum = 0;
        for (int i = start; i <= end; i++) {
            logger.debug("Поток {} просчитал значение для {} элемента", Thread.currentThread().getName(), i);
            sum += 1.0 / i / (i + 1); // функция 1 / n(n+1). Умножение в знаменателе заменено на два деления чтобы не допустить переполнения.
        }
        logger.info("Поток {} просчитал значение элементов c {} по {}. Результат {}", Thread.currentThread().getName(), start, end, sum);
        return sum;
    }
}
