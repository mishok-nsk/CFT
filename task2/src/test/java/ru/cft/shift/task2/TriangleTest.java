package ru.cft.shift.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    public void triangleNotCreate_IfSideLessThanZero() {
        double[] side = {50, -50, 500};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    public void triangleNotCreate_IfSideMoreThanMaxValue() {
        double[] side = {1050, 100, 800};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    public void triangleNotCreate_IfOnlyOneSide() {
        double[] side = {100};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    public void triangleNotCreate_IfTriangleIsDegenerate() {
        double[] side = {100, 200, 400};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    void test_RectangleFeature_IfSideCorrect() {
        double[] side = {150, 345, 250};
        Triangle tr = Triangle.create(side);
        assertEquals(side[0] + side[1] + side[2], tr.getPerimeter(), "Test triangle perimeter calculate");
        double[] angles = tr.getAngles();
        double area = 0.5 * side[1] * side[2] * Math.sin(Math.toRadians(angles[0]));
        assertEquals(area, tr.getArea(), "Test triangle area calculate");
      }
}