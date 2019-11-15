package com.myjavaproject.lab4;

import com.myjavaproject.lab4.exception.ArgsNullException;
import com.myjavaproject.lab4.exception.ExpressionNullException;
import com.myjavaproject.lab4.exception.FewArgsException;
import com.myjavaproject.lab4.exception.ParseArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringFormatterTest {

    @Test
    void format() throws ExpressionNullException, ArgsNullException, FewArgsException, ParseArgumentException {
        String strActual = StringFormatter.format("asd ${0ра}, ${2}, ${1}", "0", "1", "2");
        String strExpected = "asd 0, 2, 1";
        assertEquals(strExpected, strActual);
    }
}