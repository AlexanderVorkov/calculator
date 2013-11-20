package com.teamdev.consoleclient;

import java.math.BigDecimal;

public class Calculator {
    public static void main(String[] args) {
        BigDecimal result = new CalculatorImpl().evaluate("2+2*2");
        //BigDecimal result = new CalculatorImpl().evaluate("222");
        System.out.println("result = " + result);
    }
}
