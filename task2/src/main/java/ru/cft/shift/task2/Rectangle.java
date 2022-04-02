package ru.cft.shift.task2;

public class Rectangle extends Figure {
    private final static String NAME = "Прямоугольник";
    private final int length;
    private final int width;
    private final double diagonal;

    public static Rectangle create(int[] params) {
        if (checkParams(params)) {
            return new Rectangle(params[0], params[1]);
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

    private Rectangle(int length, int width) {
        // super();
        name = NAME;
        this.length = length;
        this.width = width;
        calcSquareAndPerimeter();
        diagonal = calcDiagonal();
        // square = length * width;
        // perimeter = 2 * (length + width);

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
