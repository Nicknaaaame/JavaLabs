package com.myjavaproject.lab3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileStatisticsTest {
    FileStatistics test = new FileStatistics("src/com/myjavaproject/lab3/text.txt");
    @Test
    void getWordsCount() {
        int actual = test.getWordsCount();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void getNumbersCount() {
        int actual = test.getNumbersCount();
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void getStringsCount() {
        int actual = test.getStringsCount();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void printRepeatedWordsCount() {
        test.printRepeatedWordsCount();
    }
}