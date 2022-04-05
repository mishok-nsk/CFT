package ru.cft.shift.task2;

abstract class Figure {
    protected final static int MAX_VALUE = 1000;
    protected String name;
    protected double square;
    protected double perimeter;

    protected void calcSquareAndPerimeter() {
        calcSquare();
        calcPerimeter();
    }

    protected abstract void calcSquare();
    protected abstract void calcPerimeter();

    public String getFeature() {
        return String.format("Тип фигуры: %s %nПлощадь: %.2f кв.мм %nПериметр: %.2f мм %n", name, square, perimeter);
    }

    public String getName() {
        return name;
    }

    public double getSquare() {
        return square;
    }

    public double getPerimeter() {
        return perimeter;
    }
}
