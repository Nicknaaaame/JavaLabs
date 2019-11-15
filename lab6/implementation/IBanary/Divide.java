package com.myjavaproject.lab6.implementation.IBanary;

import com.myjavaproject.lab6.IBinary;
import com.myjavaproject.lab6.exception.DivideByZeroException;

public class Divide implements IBinary {
    @Override
    public double calculate(double first, double second) throws DivideByZeroException {
        if(second==0)
            throw new DivideByZeroException();
        else
            return first/second;
    }
}
