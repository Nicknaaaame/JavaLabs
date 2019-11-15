package com.myjavaproject.lab5;

import javax.swing.*;

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

    static public boolean checkOnLine(Point2D ...args){
        //Point2D[] args = (Point2D[]) arg;
        if(args.length<=2)
            return true;
        for(int i = 2; i<args.length; ++i){
            double a = (double) (args[i].getX()-args[0].getX())/(double) (args[1].getX()-args[0].getX());
            double b = (double)(args[i].getY()-args[0].getY())/(double) (args[1].getY()-args[0].getY());
            if(!(a == b)){
                return false;
            }
        }
        return true;
    }
}
