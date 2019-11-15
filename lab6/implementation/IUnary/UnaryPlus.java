package com.myjavaproject.lab6.implementation.IUnary;

import com.myjavaproject.lab6.IUnary;

public class UnaryPlus implements IUnary {
    @Override
    public int calculate(int num) {
        return +num;
    }
}
