package ru.cft.shift.task2;

public class FigureCreator {

    public static Figure create(String type, double[] params) throws FigureCreateException {
        Figure figure = switch (type) {
            case "CIRCLE" -> Circle.create(params[0]);
            case "RECTANGLE" -> Rectangle.create(params);
            case "TRIANGLE" -> Triangle.create(params);
            default -> throw new FigureCreateException("Задан некорректный тип фигуры.");
        };
        if(figure == null) {
            throw new FigureCreateException("Некорректные параметры фигуры.");
        }
        return figure;
    }
}
