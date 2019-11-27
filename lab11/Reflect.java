package com.myjavaproject.lab11;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;

@Data
public class Reflect<E> {
    private E obj;

    public Reflect(E obj) {
        this.obj = obj;
    }

    public void printMethods(){
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method m : methods)
            System.out.println(m.toString());
    }

    public void printFields(){
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f: fields)
            System.out.println(f.toString());
    }

    public void printValues() throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f: fields){
            try {
                f.setAccessible(true);
                System.out.println(f.get(obj).toString());
            }
            catch (NullPointerException ex){
                System.out.println("null");
            }
        }
    }

    public void printNonEmptyFields(){
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f: fields){
            try {
                f.setAccessible(true);
                System.out.println(f.get(obj).toString());
            }
            catch (NullPointerException | IllegalAccessException ex){
            }
        }
    }

    public void printPrivateFields(){
        LinkedList<Field> allFields = new LinkedList<>(){
            {
                this.addAll(Arrays.asList(obj.getClass().getDeclaredFields()));
            }
        };
        LinkedList<Field> pubFields = new LinkedList<>(){
            {
                this.addAll(Arrays.asList(obj.getClass().getFields()));
            }
        };
        allFields.removeAll(pubFields);
        for (Field i : allFields)
            System.out.println(i.toString());
    }

    public void printPublicFields(){
        Field[] fields = obj.getClass().getFields();
        for(Field f: fields)
            System.out.println(f.toString());
    }
}
