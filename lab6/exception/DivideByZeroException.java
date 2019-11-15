package com.myjavaproject.lab6.exception;

public class DivideByZeroException extends ArithmeticException {
    public DivideByZeroException() {
        super("Divide by zero");
    }
}
