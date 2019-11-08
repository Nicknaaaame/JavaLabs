package com.myjavaproject.lab5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point2DTest {
    private Point2D firstP2D = new Point2D(1, 1);
    private Point2D secondP2D = new Point2D(-1, 7);

    @Test
    void distanceToPoint() {
        double actual = firstP2D.distanceToPoint(secondP2D);
        double expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void checkOnLine() {
        boolean actual = Point2D.checkOnLine(firstP2D, secondP2D, new Point2D(0, 4));
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void getModule() {
        double actual = secondP2D.getModule();
        double expected = 4;
        assertEquals(expected, actual);
    }


}