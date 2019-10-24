package com.myjavaproject.lab4;

import bsh.StringUtil;
import com.myjavaproject.lab4.exception.ArgsNullException;
import com.myjavaproject.lab4.exception.ExpressionNullException;
import com.myjavaproject.lab4.exception.FewArgsException;

public class StringFormatter {
    private static int num;
    private static String template = "${"+num+"}";

    /**
     * Обновляет шаблон
     */
    private static void updateTemplate(){
        template = "${"+num+"}";
    }

    /**
     * Форматирует строку
     * @param expression исходная строка
     * @param args аргументы
     * @return String - отформатированная строка
     * @throws ExpressionNullException
     * @throws ArgsNullException
     * @throws FewArgsException
     */
    public static String format(String expression, String... args) throws ExpressionNullException, ArgsNullException, FewArgsException {
        if(expression.isEmpty() || expression==null)
            throw new ExpressionNullException();

        if(args.length==0)
            throw new ArgsNullException();

        String expressionFormated = expression;
        String[] expressionArgs = StringUtil.split(expression, " ,!.?");

        int maxNum = 0;
        for(String str : expressionArgs){
            int temp = 0;
            if(str.startsWith("${")&&str.endsWith("}")) {
                temp = Integer.parseInt(str
                        .replace("${", "")
                        .replace("}", ""));
            }
            if(temp>maxNum)
                maxNum=temp;
        }

        if(maxNum>=args.length)
            throw new FewArgsException();

        for(num = 0; num<=maxNum; ++num){
            updateTemplate();
            expressionFormated = expressionFormated.replace(template, args[num]);
        }

        return expressionFormated;
    }
}
