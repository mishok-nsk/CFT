package ru.cft.shift.task2;

public class FigureCreator {

    public static Figure create(String type, int[] params) throws Exception {
        Figure figure;
        switch(type) {
            case "CIRCLE" :
                figure = Circle.create(params[0]);
                break;
            case "RECTANGLE" :
                figure = Rectangle.create(params);
                break;
            case "TRIANGLE" :
                figure = Triangle.create(params);
                break;
            default:
                throw new Exception("Задан некорректный тип фигуры.");
        }
        if(figure == null) {
            throw new Exception("Некорректные параметры фигуры.");
        }
        return figure;
    }
}
