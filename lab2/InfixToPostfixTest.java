package com.myjavaproject.lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfixToPostfixTest {
    InfixToPostfix testInfix = new InfixToPostfix("( 3 + 3 )");
   @Test
    void bktBalance() {
        boolean actual = testInfix.bktBalance();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void operationBalance() {
        boolean actual = testInfix.operationBalance();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void getPostfixStr() {
        String actual = testInfix.getPostfixStr();
        String expected  = "3 3 + ";
        assertEquals(expected, actual);
    }

    @Test
    void calculate() {
        String actual = testInfix.calculate();
        String expected  = "6.0";
        assertEquals(expected, actual);
    }
}