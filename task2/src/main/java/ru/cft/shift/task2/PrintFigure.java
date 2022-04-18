package ru.cft.shift.task2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PrintFigure {

    public static void printToFile(Figure figure, String File) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(File);) {
            pw.println(figure.getFeature());
        }
    }

    public static void printToConsole(Figure figure) {
        PrintWriter pw = new PrintWriter(System.out, true);
        pw.println(figure.getFeature());
    }
}
