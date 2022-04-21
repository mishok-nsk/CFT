package ru.cft.shift.task2;

public class Triangle extends Figure {
    private final static String NAME = "Треугольник";
    private final static String CORNER_UNIT = "град";
    private final double[] side = new double[3];
    private final double[] angle = new double[3];

    public static Triangle create(double[] params) {
        if (!checkParams(params)) {
            throw new FigureCreateException("Некорректные параметры фигуры.");
        }
        return new Triangle(params);
    }

    private static boolean checkParams(double[] params) {
        if (params.length < 3) return false;
        for (double p : params) {
            if ((p < 0) || (p > MAX_VALUE)) {
                return false;
            }
        }

        // Проверка треугольника на вырожденность. Наибольшая сторона должна быть меньше суммы двух других.
        double maxSide = Math.max(params[0], Math.max(params[1], params[2]));
        double perimeter = params[0] + params[1] + params[2];
        return ((perimeter / maxSide) > 2.0);
    }

    private Triangle(double[] side) {
        super(NAME);
        System.arraycopy(side, 0, this.side, 0, 3);
        calcCorners();
    }

    @Override
    protected double calcArea() {
        return area = 0.5 * side[1] * side[2] * Math.sin(Math.toRadians(angle[0]));
    }

    @Override
    protected double calcPerimeter() {
        return perimeter =  side[0] + side[1] + side[2];
    }

    private void calcCorners() {
        double cornerRadian = calcAngle(side[1], side[2], side[0]);
        angle[0] =  cornerRadian * 180 / Math.PI;
        cornerRadian = calcAngle(side[2], side[0], side[1]);
        angle[1] = cornerRadian * 180 / Math.PI;
        angle[2] = 180 - angle[0] - angle[1];
    }

    private double calcAngle(double side1, double side2, double side3) {
        return Math.acos((side1 * side1 + side2 * side2 - side3 * side3) / (2 * side1 * side2));
    }

    @Override
    public String getFeature() {
        return super.getFeature() + String.format("Сторона1: %.0f %7$s %nУгол1: %.2f %8$s %n" +
                "Сторона2: %.0f %7$s %nУгол2: %.2f %8$s %nСторона3: %.0f %7$s %nУгол3: %.2f %s",
                side[0], angle[0], side[1], angle[1], side[2], angle[2], LENGTH_UNIT, CORNER_UNIT);
    }

    public double[] getAngles() {
        return angle;
    }
}
