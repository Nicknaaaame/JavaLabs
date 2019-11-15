package com.myjavaproject.lab6.implementation.IUnary;

import com.myjavaproject.lab6.IUnary;

public class UnaryMinus implements IUnary {
    @Override
    public int calculate(int num) {
        return -num;
    }
}
