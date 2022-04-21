package ru.cft.shift.task2;

public class Rectangle extends Figure {
    private final static String NAME = "Прямоугольник";
    private final double length;
    private final double width;
    private final double diagonal;

    public static Rectangle create(double[] params) {
        if (!checkParams(params)) {
            throw new FigureCreateException("Некорректные параметры фигуры.");
        }
        return new Rectangle(params);
    }

    private static boolean checkParams(double[] params) {
        if (params.length < 2) return false;
        for (double p : params) {
            if ((p <= 0) || (p > MAX_VALUE)) {
                return false;
            }
        }
        return true;
    }

    private Rectangle(double[] side) {
        super(NAME);
        this.length = Math.max(side[0], side[1]);
        this.width = Math.min(side[0], side[1]);
        diagonal = calcDiagonal();
    }

    @Override
    protected double calcArea() {
        return area = length * width;
    }

    @Override
    protected double calcPerimeter() {
        return perimeter = 2 * (length + width);
    }

    private double calcDiagonal() {
        return Math.sqrt(length * length + width * width);
    }

    @Override
    public String getFeature() {
        return super.getFeature() +
                String.format("Длина: %.0f %4$s %nШирина: %.0f %4$s %nДиагональ: %.2f %s", length, width, diagonal, LENGTH_UNIT);
    }

    public double getDiagonal() {
        return diagonal;
    }
}
