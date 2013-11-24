package com.teamdev.calculator;

import java.math.BigDecimal;
import java.util.Scanner;

public class RunCalculator {
    public static void main(String[] args) {
        String inputExpression = "";
        String exitKey = "exit";
        try {
            do {
                System.out.println("Enter an expression or \"exit\" for exit.");

                Scanner scan = new Scanner(System.in);
                inputExpression = scan.nextLine();
                if (inputExpression.equals(exitKey)) {
                    System.exit(0);
                }

                BigDecimal result = new Calculator().evaluate(inputExpression);

                System.out.println("Result: " + result);
            }
            while (true);
        } catch (BinaryOperatorException binaryOperatorException) {
            System.out.println(binaryOperatorException.getMessage());
        } catch (CalculatorException calculatorException) {
            System.out.println(calculatorException.getMessage() + " at the position " + (calculatorException.getErrorPosition() + 1));

        }
    }
}
