package ru.cft.shift.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFigureFromFile {
    private final String typeFigure;
    private final double[] params;

    public ReadFigureFromFile(String file) throws FigureReadException, FileNotFoundException {
        try (Scanner sc = new Scanner(new File(file))) {
            String stringParams;
            if (sc.hasNextLine()) typeFigure = sc.nextLine();
            else throw new FigureReadException("Файл пуст.");
            if (sc.hasNextLine()) stringParams = sc.nextLine();
            else throw new FigureReadException("Параметры фигуры не заданы.");
            String[] stringParamsArr = stringParams.split(" ");
            int length = stringParamsArr.length;
            if (length == 0) {
                throw new FigureReadException("Параметры фигуры не заданы.");
            }
            params = new double[length];
            for (int i = 0; i < length; i++) {
                params[i] = Double.parseDouble(stringParamsArr[i]);
            }
        }
        catch (NumberFormatException e) {
            throw new FigureReadException("Некорректные параметры фигуры.");
        }
    }

    public String getTypeFigure() {
        return typeFigure;
    }

    public double[] getParams() {
        return params;
    }
}
