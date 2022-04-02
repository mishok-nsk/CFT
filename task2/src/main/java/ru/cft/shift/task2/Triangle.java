package ru.cft.shift.task2;

public class Triangle extends Figure {
    private final static String NAME = "Треугольник";
    private final int[] side = new int[3];
    private final double[] corner = new double[3];

    public static Triangle create(int[] params) {
        if (checkParams(params)) {
            return new Triangle(params);
        }
        return null;
    }

    private static boolean checkParams(int[] params) {
        if (params.length < 3) return false;
        for (double p : params) {
            if (p < 0 || p > MAX_VALUE) {
                return false;
            }
        }
        if (1.0 * (params[0] + params[1] + params[2]) / Math.max(params[0], Math.max(params[1], params[2])) <= 2.0) {
            return false;
        }
        return true;
    }

    private Triangle(int[] side) {
        name = NAME;
        System.arraycopy(side, 0, this.side, 0, 3);
        calcCorner();
        calcSquareAndPerimeter();
    }

    @Override
    protected void calcSquare() {
        square = 0.5 * side[1] * side[2] * Math.sin(Math.toRadians(corner[0]));
    }

    @Override
    protected void calcPerimeter() {
        perimeter =  side[0] + side[1] + side[2];
    }

    private void calcCorner() {
        double cornerRadian = Math.acos(1.0 * (side[1] * side[1] + side[2] * side[2] - side[0] * side[0]) / (2 * side[1] * side[2]));
        corner[0] =  cornerRadian * 180 / Math.PI;
        cornerRadian = Math.acos(1.0 * (side[0] * side[0] + side[2] * side[2] - side[1] * side[1]) / (2 * side[0] * side[2]));
        corner[1] = cornerRadian * 180 / Math.PI;
        corner[2] = 180 - corner[0] - corner[1];
    }

    @Override
    public String getFeature() {
        return super.getFeature() + String.format("Сторона1: %d мм%nУгол1: %.2f град%n" +
                "Сторона2: %d мм%nУгол2: %.2f град%nСторона3: %d мм%nУгол3: %.2f град",
                side[0], corner[0], side[1], corner[1], side[2], corner[2]);
    }

    public double[] getCorner() {
        return corner;
    }
}
