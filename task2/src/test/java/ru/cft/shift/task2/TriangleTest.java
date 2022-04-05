package ru.cft.shift.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    public void triangleNotCreate_IfSideLessThanZero() {
        int[] side = {50, -50, 500};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    public void triangleNotCreate_IfSideMoreThanMaxValue() {
        int[] side = {1050, 100, 800};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    public void triangleNotCreate_IfOnlyOneSide() {
        int[] side = {100};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    public void triangleNotCreate_IfTriangleIsDegenerate() {
        int[] side = {100, 200, 400};
        Triangle tr = Triangle.create(side);
        assertNull(tr);
    }

    @Test
    void test_RectangleFeature_IfSideCorrect() {
        int[] side = {150, 345, 250};
        Triangle tr = Triangle.create(side);
        assertEquals(side[0] + side[1] + side[2], tr.getPerimeter());
        double[] corners = tr.getCorner();
        double square = 0.5 * side[1] * side[2] * Math.sin(Math.toRadians(corners[0]));
        assertEquals(square, tr.getSquare());
      }
}