package ru.cft.shift.task2;

public class FigureCreator {

    public static Figure create(String type, double[] params) {
        return switch (type) {
                case "CIRCLE" -> Circle.create(params[0]);
                case "RECTANGLE" -> Rectangle.create(params);
                case "TRIANGLE" -> Triangle.create(params);
                default -> throw new FigureCreateException("Задан некорректный тип фигуры.");
            };
    }
}
