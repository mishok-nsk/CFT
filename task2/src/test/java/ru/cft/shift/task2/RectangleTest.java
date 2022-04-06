package ru.cft.shift.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    public void rectangleNotCreate_IfSideLessThanZero() {
        double[] side = {50, -50};
        Rectangle rc = Rectangle.create(side);
        assertNull(rc);
    }

    @Test
    public void rectangleNotCreate_IfSideMoreThanMaxValue() {
        double[] side = {1050, 800};
        Rectangle rc = Rectangle.create(side);
        assertNull(rc);
    }

    @Test
    public void rectangleNotCreate_IfOnlyOneSide() {
        double[] side = {100};
        Rectangle rc = Rectangle.create(side);
        assertNull(rc);
    }

    @Test
    void test_RectangleFeature_IfSideCorrect() {
        double[] side = {150, 345};
        Rectangle rc = Rectangle.create(side);
        assertEquals(2 * (side[0] + side[1]), rc.getPerimeter());
        assertEquals(side[0] * side[1], rc.getArea());
        double diagonal = Math.sqrt(side[0] * side[0] + side[1] * side[1]);
        assertEquals(diagonal, rc.getDiagonal());
    }
}