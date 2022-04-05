package ru.cft.shift.task2;

public class Rectangle extends Figure {
    private final static String NAME = "Прямоугольник";
    private final int length;
    private final int width;
    private final double diagonal;

    public static Rectangle create(int[] params) {
        if (checkParams(params)) {
            return new Rectangle(params);
        }
        return null;
    }

    private static boolean checkParams(int[] params) {
        if (params.length < 2) return false;
        for (int p : params) {
            if (p < 0 || p > MAX_VALUE) {
                return false;
            }
        }
        return true;
    }

    private Rectangle(int[] side) {
        name = NAME;
        this.length = Math.max(side[0], side[1]);
        this.width = Math.min(side[0], side[1]);
        calcSquareAndPerimeter();
        diagonal = calcDiagonal();
    }

    @Override
    protected void calcSquare() {
        square = length * width;
    }

    @Override
    protected void calcPerimeter() {
        perimeter = 2 * (length + width);
    }

    private double calcDiagonal() {
        return Math.sqrt(length * length + width * width);
    }

    @Override
    public String getFeature() {
        return super.getFeature() +
                "Длина: " + length + "мм\n" +
                "Ширина: " + width + "мм\n" +
                String.format("Диагональ: %.2f мм", diagonal);
    }

    public double getDiagonal() {
        return diagonal;
    }
}
