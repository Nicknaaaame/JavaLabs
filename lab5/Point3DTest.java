package com.myjavaproject.lab5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    private Point3D firstP3D = new Point3D(1, -2, 0);
    private Point3D secondP3D = new Point3D(2, 0, -1);

    @Test
    void distanceToPoint() {
        double actual = firstP3D.distanceToPoint(secondP3D);
        double expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void checkOnPlane() {
        boolean actual = Point3D.checkOnPlane(firstP3D, secondP3D, new Point3D(0, -1, 2), new Point3D(2, 0, -1));
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void getModule() {
        double actual = firstP3D.getModule();
        double expected = 5;
        assertEquals(expected, actual);
    }
}