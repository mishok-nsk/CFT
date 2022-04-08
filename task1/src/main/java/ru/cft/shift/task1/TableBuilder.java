package ru.cft.shift.task1;

import java.io.PrintWriter;

public class TableBuilder {

    private static final String SPACE = " ";
    private static final String MINUS = "-";
    private static final char PLUS = '+';
    private static final String NEXT_LINE = "\n";
    private static final char PIPE = '|';

    private final int size;
    private final int cellsWidth;
    private final int firstColWidth;

    public TableBuilder(int initSize) {
        size = initSize;
        cellsWidth = countDigits(size * size);
        firstColWidth = countDigits(size);
    }

    public void printToConsole() {
        PrintWriter pw = new PrintWriter(System.out, true);
        print(pw);
    }

    private void print(PrintWriter pw) {
        StringBuilder tableBuilder = new StringBuilder(calculateTableCapacity());
        tableBuilder.append(SPACE.repeat(firstColWidth));
        for (int j = 1; j <= size; j++) {
            tableBuilder.append(PIPE);
            tableBuilder.append(format(j, cellsWidth));
        }
        tableBuilder.append(NEXT_LINE);
        String separatorString = createRowSeparator();
        tableBuilder.append(separatorString);

        for (int i = 1; i <= size; i++) {
            tableBuilder.append(format(i, firstColWidth));
            for (int j = 1; j <= size; j++) {
                tableBuilder.append(PIPE);
                tableBuilder.append(format(i * j, cellsWidth));
            }
            tableBuilder.append(NEXT_LINE);
            tableBuilder.append(separatorString);
        }
        pw.println(tableBuilder);
    }

    private int calculateTableCapacity() {
        return calculateRowCapacity() * (size + 1) * 2;
    }

    private int calculateRowCapacity() {
        return firstColWidth + ((cellsWidth + 1) * size) + 1;
    }

    private int countDigits(int num) {
        int count = 0;
        while (num != 0) {
            count++;
            num /= 10;
        }
        return count;
    }

    private String format(int num, int size) {
        return SPACE.repeat(size - countDigits(num)) + num;
    }

    private String createRowSeparator() {
        StringBuilder out = new StringBuilder(calculateRowCapacity());
        for (int i = 0; i < size; i++) {
            int cellWidth = (i == 0) ? firstColWidth : cellsWidth;
            out.append(MINUS.repeat(cellWidth));
            out.append(PLUS);
        }
        out.append(MINUS.repeat(cellsWidth));
        out.append(NEXT_LINE);
        return out.toString();
    }
}
