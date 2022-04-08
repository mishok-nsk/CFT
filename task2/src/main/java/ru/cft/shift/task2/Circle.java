package ru.cft.shift.task2;

public class Circle extends Figure {
    private final static String NAME = "Круг";
    private final double radius;
    private final double diameter;

    public static Circle create(double radius) {
        if (checkParams(radius)) {
            return new Circle(radius);
        }
        return null;
    }

    private static boolean checkParams(double radius) {
        return (radius > 0) && (radius < MAX_VALUE);
    }

    private Circle(double radius) {
        super(NAME);
        this.radius = radius;
        diameter = radius * 2;
    }

    @Override
    public String getFeature() {
        return super.getFeature() + String.format("Радиус: %.0f %3$s %nДиаметр: %.0f %s", radius, diameter, LENGTH_UNIT);
    }

    @Override
    protected double calcPerimeter() {
        return perimeter =  Math.PI * diameter;
    }

    @Override
    protected double calcArea() {
        return area = radius * radius * Math.PI;
    }
}
