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

    static public boolean checkOnLine(Point3D ...args){
        //Point2D[] args = (Point2D[]) arg.clone();
        if(args.length==2)
            return true;
        for(int i = 2; i<args.length; ++i){
            double a = (double) (args[i].getX()-args[0].getX())/(double) (args[1].getX()-args[0].getX());
            double b = (double) (args[i].getY()-args[0].getY())/(double) (args[1].getY()-args[0].getY());
            double d = (double) (args[i].getZ()-args[0].getZ())/(double) (args[1].getZ()-args[0].getZ());
            if(!(a == b && a == d))
                return false;
        }
        return true;
    }

    static public boolean checkOnPlane(Point3D ...args){
        if(args.length<=3)
            return true;

        int[][] matrix = {
                {0, args[1].getX()-args[0].getX(), args[2].getX()-args[0].getX()},
                {0, args[1].getY()-args[0].getY(), args[2].getY()-args[0].getY()},
                {0, args[1].getZ()-args[0].getZ(), args[2].getZ()-args[0].getZ()}
        };

        for (int i = 3; i<args.length; ++i){
            matrix[0][0] = args[i].getX()-args[0].getX();
            matrix[1][0] = args[i].getY()-args[0].getY();
            matrix[2][0] = args[i].getZ()-args[0].getZ();
            if(!checkDeterminantIsNull(matrix))
                return false;
        }

        return true;
    }

    static private boolean checkDeterminantIsNull(int[][] m){
        int determinant =
                        m[0][0]*m[1][1]*m[2][2]
                        +m[0][1]*m[1][2]*m[2][0]
                        +m[1][0]*m[2][1]*m[0][2]
                        -m[0][2]*m[1][1]*m[2][0]
                        -m[1][0]*m[0][1]*m[2][2]
                        -m[2][1]*m[1][2]*m[0][0];
        return determinant == 0;
    }
}
