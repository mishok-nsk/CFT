package ru.cft.shift.task2;

public class Circle extends Figure {
    private final static String NAME = "Круг";
    private final int radius;
    private final int diameter;

    public static Circle create(int radius) {
        if (checkParams(radius)) {
            return new Circle(radius);
        }
        return null;
    }

    private static boolean checkParams(int radius) {
        if (radius > 0 && radius < MAX_VALUE) {
            return true;
        }
        return false;
    }

    private Circle(int radius) {
        super();
        name = NAME;
        this.radius = radius;
        diameter = radius * 2;
        calcSquareAndPerimeter();
    }

    @Override
    protected void calcSquare() {
        square = radius * radius * Math.PI;
    }

    @Override
    protected void calcPerimeter() {
        perimeter =  Math.PI * diameter;
    }

    @Override
    public String getFeature() {
        return super.getFeature() + String.format("Радиус: %d мм%nДиаметр: %d мм", radius, diameter);
    }
}
