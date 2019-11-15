package com.myjavaproject.lab2;

import java.text.Format;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;


public class InfixToPostfix {
    private String strInfix;
    private Stack<String> stack = new Stack<>();
    private HashMap<String, Integer> hashPriority = new HashMap<>() {
        {
            put("(", 1);
            put(")", 2);
            put("=", 3);
            put("+", 4);
            put("-", 4);
            put("*", 5);
            put("/", 5);
        }
    };

    /**
     * Конструктор - инициализирует: strInfix
     * @param strInfix - инфиксная запись
     */
    public InfixToPostfix(String strInfix) {
        this.strInfix = Objects.requireNonNullElse(strInfix, "");//хз что тут правильней null или ""
    }

    /**
     * Проверяет баланс скобок
     * @return boolean: true - сбалансировано, false - нет
     */
    public boolean bktBalance() {
        if(strInfix.equals(""))
            return false;
        String strTmp;
        for (int i = 0; i < strInfix.length(); ++i)
            try {
                strTmp = strInfix.substring(i, i + 1);
                if (!strTmp.equals(" "))
                    if (strTmp.equals("("))
                        stack.push(strTmp);
                    else if (strTmp.equals(")"))
                        if (stack.pop().equals(")"))
                            return false;
            } catch (EmptyStackException ex) {
                return false;
            }
        if (!stack.empty()) {
            stack.clear();
            return false;
        }
        return true;
    }

    /**
     * Проверяет баланс операций
     * @return boolean: true - сбалансировано, false - нет
     */
    public boolean operationBalance() {
        if(strInfix.equals(""))
            return false;
        String leftBkt = "(", rightBkt = ")";
        String strCheckOper = strInfix.replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "");
        for (int i = 0; i < strCheckOper.length(); ++i)
            try {
                if (hashPriority.containsKey(strCheckOper.substring(i, i + 1))) {
                    if (!hashPriority.containsKey(strCheckOper.substring(i - 1, i)) && !hashPriority.containsKey(strCheckOper.substring(i + 1, i + 2)))
                        i++;
                    else
                        return false;
                }
            } catch (StringIndexOutOfBoundsException ex) {
                return false;
            }
        return true;
    }

    /**
     * Форматирует инфиксную запись в постфиксную
     * @return String - постфиксная запись
     */
    public String getPostfixStr() {
        if(strInfix.equals(""))
            return "";
        StringBuilder strPostfix = new StringBuilder();
        String strTmp;
        if (!bktBalance() || !operationBalance())
            return null;
        for (int i = 0; i < strInfix.length(); ++i) {
            try {
                //Добавляет числа
                if (strInfix.charAt(i) != ' ')
                    if (!hashPriority.containsKey(String.valueOf(strInfix.charAt(i)))) {
                        strPostfix.append(strInfix.charAt(i));
                        i++;
                        //Пока не встретит пробел
                        while (strInfix.length() > i && !String.valueOf(strInfix.charAt(i)).equals(" ")) {
                            strPostfix.append(strInfix.charAt(i));
                            i++;
                        }
                        strPostfix.append(" ");
                    } else {
                        //Для пустого стэка
                        if (stack.empty()) {
                            stack.push(String.valueOf(strInfix.charAt(i)));
                        } else {
                            //Если встретилась закрывающая скобка, то удаляем все до первой открывающей скобки включительно
                            if (hashPriority.get(String.valueOf(strInfix.charAt(i))) == 2) {
                                while (!stack.peek().equals("(")) {
                                    strTmp = stack.pop();
                                    strPostfix.append(strTmp).append(" ");
                                }
                                stack.pop();
                            } else {
                                //Открывающая скобка имеет особый приоритет
                                if (hashPriority.get(String.valueOf(strInfix.charAt(i))) != 1) {
                                    //Очистить до приоритета меньше приоритета входящего символа
                                    while (!stack.empty() && hashPriority.get(String.valueOf(strInfix.charAt(i))) <= hashPriority.get(stack.peek()))
                                        strPostfix.append(stack.pop()).append(" ");
                                }
                                //Добавить входящий символ
                                stack.push(String.valueOf(strInfix.charAt(i)));
                            }
                        }
                    }
            } catch (EmptyStackException exc) {
                return null;
            }
        }
        while (!stack.empty()) {
            strTmp = stack.pop();
            if (strTmp.equals(")") || strTmp.equals("("))
                return null;
            strPostfix.append(strTmp).append(" ");
        }
        return strPostfix.toString();
    }

    /**
     * Результат операции над числами
     * @param first - первое число
     * @param second - второе число
     * @param operation - операция
     * @return double - результат операции двух чисел
     */
    //принимает первый операнд и второй принимает операцию
    private double operation(double first, double second, String operation) {
        double result = 0;
        switch (operation) {
            case "+":
                result = first + second;
                break;
            case "-":
                result = first - second;
                break;
            case "*":
                result = first * second;
                break;
            case "/":
                result = first / second;
                break;
        }
        return result;
    }
    /**
     * Калькулятор постфиксной записи
     * @return String - число, что получилось
     */
    public String calculate() {
        String strPostfix = getPostfixStr();
        if(strPostfix.equals(""))
            return "";
        double result = 0;
        for (int i = 0; i < strPostfix.length(); ++i) {
            if (strPostfix.charAt(i) != ' ')
                if (!hashPriority.containsKey(String.valueOf(strPostfix.charAt(i)))) {
                    StringBuilder strTmp = new StringBuilder();
                    strTmp.append(strPostfix.charAt(i));
                    i++;
                    while (strPostfix.length() > i && !String.valueOf(strPostfix.charAt(i)).equals(" ")) {
                        strTmp.append(strPostfix.charAt(i));
                        i++;
                    }
                    stack.push(strTmp.toString());
                } else
                    try {
                        String second = stack.pop(), first = stack.pop();
                        double tempResult = operation(Double.parseDouble(first), Double.parseDouble(second), String.valueOf(strPostfix.charAt(i)));
                        stack.push(String.valueOf(tempResult));
                    } catch (EmptyStackException ex) {
                        return "????";
                    }
        }
        return stack.pop();
    }
}
