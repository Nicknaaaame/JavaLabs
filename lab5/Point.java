package com.myjavaproject.lab5;

public class Point {
    private int x;

    /**
     * Конструктор - задает координаты
     * @param x
     */
    public Point(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Высчитывает дистанцию до другой точки
     * @param obj другая точка
     * @return дистанция
     */
    public double distanceToPoint(Object obj){
        Point p = (Point) obj;
        int delX = p.getX()-x;
        return Math.sqrt((double)(delX*delX));
    }

    /**
     * Высчитывает модуль точки
     * @return модуль
     */
    public double getModule(){
        return Math.sqrt((double)(x*x));
    }
}
