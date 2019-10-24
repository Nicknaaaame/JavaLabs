package com.myjavaproject.lab5;

public class Point2D extends Point {
    private int y;

    /**
     * Констркутор - задает координаты
     * @param x
     * @param y
     */
    public Point2D(int x, int y) {
        super(x);
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public double distanceToPoint(Object obj) {
        Point2D p2d = (Point2D) obj;
        int delX = p2d.getX() - getX();
        int delY = p2d.y - y;
        return Math.sqrt((double)(delX*delX+delY*delY));
    }

    @Override
    public double getModule(){
        return Math.sqrt((double)(getX()*getX()+y*y));
    }
}
