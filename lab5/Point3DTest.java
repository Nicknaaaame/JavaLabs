package com.myjavaproject.lab5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    Point3D firstP3D = new Point3D(0, 0, 5);
    Point3D secondP3D = new Point3D(0, 4, 5);

    @Test
    void distanceToPoint() {
        double actual = firstP3D.distanceToPoint(secondP3D);
        double expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void getModule() {
        double actual = firstP3D.getModule();
        double expected = 5;
        assertEquals(expected, actual);
    }
}