package ru.cft.shift.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            logger.info("Введите количество элементов вычисления:");
            String input = in.nextLine();
            int number = Integer.parseInt(input);
            if (number < 1) {
                logger.info("Введенное некорректное число.");
                return;
            }

            ConcurrentCalculate task = new ConcurrentCalculate(number);
            task.calculate();

        } catch (NoSuchElementException e) {
            logger.error("Данные не введены.");
        } catch (NumberFormatException e) {
            logger.error("Введены некорректные данные.");
        }
    }
}
