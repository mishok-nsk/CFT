package ru.cft.shift.task1;

import java.util.Scanner;
import java.util.InputMismatchException;

public class MultiplicationTable {

    private static final int MAX_SIZE = 32;

    private final int size;
    private final int cellsWidth;
    private final int firstColWidth;
    private final String table;

    private int countOfDigits (int num) {
        int count = (num == 0) ? 1 : 0;
        while (num != 0) {
            count++;
            num /= 10;
        }
        return count;
    }

    private String format (int num, int size) {
        StringBuilder out = new StringBuilder();
        int count = countOfDigits(num);
        out.append(" ".repeat(size - count));
        if (num != 0) {
            out.append(num);
        }
        else
            out.append(' ');
        return out.toString();
    }

    private String separator () {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int cellWidth = (i == 0) ? firstColWidth : cellsWidth;
            out.append("-".repeat(cellWidth));
            out.append('+');
        }
        out.append("-".repeat(cellsWidth));
        out.append("\n");
        return out.toString();
    }

    public MultiplicationTable (int initSize) {
        size = initSize;
        cellsWidth = countOfDigits(size*size);
        firstColWidth = countOfDigits(size);
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append(format(0, firstColWidth));
        for (int j = 1; j <= size; j++) {
            tableBuilder.append('|');
            tableBuilder.append(format(j, cellsWidth));
        }
        tableBuilder.append("\n");

        String separatorString = separator();

        for (int i = 1; i <= size; i++) {
            tableBuilder.append(separatorString);
            tableBuilder.append(format(i, firstColWidth));
            for (int j = 1; j <= size; j++) {
                tableBuilder.append('|');
                tableBuilder.append(format(i*j, cellsWidth));
            }
            tableBuilder.append("\n");
        }
        table = tableBuilder.toString();
    }

    public String toString () {
        return table;
    }

    public static void main (String[] args) {
        System.out.print("Введите размер таблицы умножения(число от 1 до " + MAX_SIZE + "):");
        Scanner in = new Scanner(System.in);
        int size = 0;
        int trial = 10;
        boolean correctInput = false;
        while (trial > 0) {
            try {
                size = in.nextInt();
            }
            catch (InputMismatchException e) {
                try {
                    in.nextLine();
                }
                catch(Exception ignore) {
                }
                System.out.println("Введены некоррекные данные");
                System.out.print("Введите размер таблицы умножения(число от 1 до " + MAX_SIZE + "):");
                trial--;
                continue;
            }

            if (size > MAX_SIZE || size < 1) {
                System.out.println("Введенное число не попадает в требуемый диапазон");
                System.out.print("Введите размер таблицы умножения(число от 1 до " + MAX_SIZE + "):");
                trial--;
                continue;
            }
            correctInput = true;
            break;
        }

        if (!correctInput) {
            System.out.print("\nВам не удалось ввести корректные данные. Попробуйте позже.");
            in.close();
            return;
        }

        MultiplicationTable mt= new MultiplicationTable(size);
        System.out.println(mt);
        in.close();
    }
}
