package ru.cft.shift.task1;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MultiplicationTable {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 32;

    public static void main (String[] args) {

        int size;

        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Введите размер таблицы умножения(число от " + MIN_SIZE + " до " + MAX_SIZE + "):");
            String input = in.nextLine();
            size = Integer.parseInt(input);
        } catch (NoSuchElementException e) {
            System.out.println("Данные не введены.");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Введены некорректные данные.");
            return;
        }

        if (size > MAX_SIZE || size < MIN_SIZE) {
            System.out.println("Введенное число не попадает в требуемый диапазон.");
            return;
        }
        TableBuilder tb= new TableBuilder(size);
        tb.printToConsole();
    }
}
