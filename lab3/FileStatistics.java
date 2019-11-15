package com.myjavaproject.lab3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileStatistics {
    private FileReader fileReader;
    private BufferedReader buffReader;

    /**
     * Конструктор - инициализирует: fileReader, buffReader
     * @param path - путь к файлу
     */
    public FileStatistics(String path){
        try {
            fileReader = new FileReader(path);
            buffReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Считает количество слов
     * @return int - количество слов
     */
    public int getWordsCount(){
        int count = 0;
        Scanner scanner = new Scanner(fileReader);
        String strCurrent;
        while (scanner.hasNext()){
            strCurrent = scanner.next();
            int temp = 0;
            for(int i = 0; i<strCurrent.length(); ++i){
                if(strCurrent.charAt(i)>'9' || strCurrent.charAt(i)<'0' && strCurrent.charAt(i)!='.')
                    break;
                temp++;
            }
            if(temp!=strCurrent.length())
                count++;
        }
        return count;
    }

    /**
     * Считает количество чисел
     * @return int - количество чисел
     */
    public int getNumbersCount(){
        int count = 0;
        try {
            int ch = fileReader.read();
            while(ch!=-1){
                if(ch >= '0' && ch <= '9'){
                    while (ch!=' ' && ch!=-1 && ch!='\n')
                        ch = fileReader.read();
                    count++;
                }
               ch = fileReader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Считает количество строк
     * @return int - количество строк
     */
    public int getStringsCount(){
        int count = 0;
        String lines = null;
        try {
            lines = buffReader.readLine();
            while (lines != null){
                count++;
                lines = buffReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Выписывает: слово - количество повторов
     */
    public void printRepeatedWordsCount(){
        int count = 0;
        HashMap<String, Integer> hashMap = new HashMap<>();
        String strCurrent;
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNext()){
            strCurrent = scanner.next();
            if(hashMap.containsKey(strCurrent))
                hashMap.put(strCurrent, hashMap.get(strCurrent)+1);
            else
                hashMap.put(strCurrent, 1);
        }
        for (Map.Entry<String,Integer> entry : hashMap.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
