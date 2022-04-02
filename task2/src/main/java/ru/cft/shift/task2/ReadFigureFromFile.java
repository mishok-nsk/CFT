package ru.cft.shift.task2;

import java.io.File;
import java.util.Scanner;

public class ReadFigureFromFile {
    private final String typeFigure;
    private final int[] params;

    public ReadFigureFromFile(String file) throws Exception {
        String stringParams;
        try (Scanner sc = new Scanner(new File(file))) {
            //typeFigure = sc.nextLine();
            if (sc.hasNextLine()) typeFigure = sc.nextLine();
            else throw new Exception("Файл пуст.");
            if (sc.hasNextLine()) stringParams = sc.nextLine();
            else throw new Exception("Параметры фигуры не заданы.");
        }
        catch (Exception e) {
            //System.out.println(e.getMessage());
            throw e;
        }

        String[] stringParamsArr = stringParams.split(" ");
        int length = stringParamsArr.length;
        if (length == 0) {
            throw new Exception("Параметры фигуры не заданы.");
        }
        params = new int[length];
        try {
            for (int i = 0; i < length; i++) {
                params[i] = Integer.parseInt(stringParamsArr[i]);
            }
        }
        catch (NumberFormatException e) {
            throw new Exception("Некорректные параметры фигуры.");
        }
    }

    public String getTypeFigure() {
        return typeFigure;
    }

    public int[] getParams() {
        return params;
    }
}
