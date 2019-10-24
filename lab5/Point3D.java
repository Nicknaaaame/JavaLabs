package com.myjavaproject.lab5;

public class Point3D extends Point2D {
    private int z;

    /**
     * Констркутор - задает координаты
     * @param x
     * @param y
     * @param z
     */
    public Point3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public double distanceToPoint(Object obj) {
        Point3D p3d = (Point3D) obj;
        int delX = p3d.getX() - getX();
        int delY = p3d.getY() - getX();
        int delZ = p3d.z - z;
        return Math.sqrt((double)(delX*delX+delY*delY+delZ*delZ));
    }

    @Override
    public double getModule(){
        return Math.sqrt((double)(getX()*getX()+getY()*getY()+z*z));
    }
}
