package ru.cft.shift.task2;

abstract class Figure {
    protected final static double MAX_VALUE = 1000;
    protected final static String LENGTH_UNIT = "мм";
    protected final static String AREA_UNIT = "кв.мм";
    protected final String name;
    protected double area;
    protected double perimeter;

    public Figure(String name) {
        this.name = name;
    }
    public String getFeature() {
        area = calcArea();
        perimeter = calcPerimeter();
        return String.format("Тип фигуры: %s %nПлощадь: %.2f %s %nПериметр: %.2f %s %n", name, area, AREA_UNIT, perimeter, LENGTH_UNIT);
    }
    String getName() {
        return name;
    }

    double getArea() {
        if (area == 0) {
            area = calcArea();
        }
        return area;
    }

    double getPerimeter() {
        if (perimeter == 0) {
            perimeter = calcPerimeter();
        }
        return perimeter;
    }

    protected abstract double calcArea();

    protected abstract double calcPerimeter();
}
