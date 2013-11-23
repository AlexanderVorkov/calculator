package com.teamdev.calculator;

import java.math.BigDecimal;

public class RunCalculator {
    public static void main(String[] args) {
        BigDecimal result = new Calculator().evaluate("2+3");
        System.out.println("====result = " + result);
    }
}
