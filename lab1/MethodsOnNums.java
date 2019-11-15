package com.myjavaproject.lab1;

public class MethodsOnNums {
    int[] arrInt;
    //конструктор принимает массив
    public MethodsOnNums(int[] arrInt) {
        this.arrInt = arrInt;
    }
    //возвращает максимальное значение массива
    public int getMaxNum() {
        int maxNum = arrInt[0];
        for (int i = 1; i < arrInt.length; ++i)
            if (maxNum < arrInt[i])
                maxNum = arrInt[i];
        return maxNum;
    }
    //возвращает минимальное значение массива
    public int getMinNum() {
        int minNUm = arrInt[0];
        for (int i = 1; i < arrInt.length; ++i) {
            if (minNUm > arrInt[i])
                minNUm = arrInt[i];
        }
        return minNUm;
    }
    //возвращает среднее арифметическое
    public double getAverageArithmetic() {
        double average = 0;
        for (int i = 0; i < arrInt.length; ++i)
            average += arrInt[i];
        return average / arrInt.length;
    }
    //сортировка пузырьковая принимает массив, возвращает отсортированный массив
    private int[] bubbleSort(int[] numArray) {

        int n = numArray.length;
        int temp = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (numArray[j - 1] > numArray[j]) {
                    temp = numArray[j - 1];
                    numArray[j - 1] = numArray[j];
                    numArray[j] = temp;
                }

            }
        }
        return numArray;
    }
    //возвращает медианное значение
    public int getMedian() {
        int median = 0;
        if (arrInt.length % 2 == 0)
            median = bubbleSort(arrInt)[arrInt.length / 2 - 1];
        else
            median = bubbleSort(arrInt)[arrInt.length / 2];
        return median;
    }
    //возвращает среднее геометрическое
    public double getAverageGeometric() {
        double multiply = 1;
        for (int i = 0; i < arrInt.length; ++i)
            multiply *= arrInt[i];
        return Math.pow(multiply, 1 / (double)arrInt.length);
    }
}
