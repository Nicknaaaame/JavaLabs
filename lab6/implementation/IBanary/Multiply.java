package com.myjavaproject.lab6.implementation.IBanary;

import com.myjavaproject.lab6.IBinary;

public class Multiply implements IBinary {
    @Override
    public double calculate(double first, double second) {
        return first*second;
    }
}
