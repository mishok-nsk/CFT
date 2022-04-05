package ru.cft.shift.task2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    public void circleNotCreate_IfRadiusLessThanZero() {
        int radius = -5;
        Circle cr = Circle.create(radius);
        assertNull(cr);
    }

    @Test
    public void circleNotCreate_IfRadiusMoreThanMaxValue() {
        int radius = 1050;
        Circle cr = Circle.create(radius);
        assertNull(cr);
    }

    @Test
    void test_PerimeterAndSquare_IfRadiusCorrect() {
        int radius = 456;
        Circle cr = Circle.create(radius);
        assertEquals(2 * Math.PI * radius, cr.getPerimeter());
        assertEquals(Math.PI * radius * radius, cr.getSquare());
    }
}