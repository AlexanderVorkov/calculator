package com.teamdev.calculator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class CalculatorExpressionStack implements AbstractExpressionStack{
    public HashMap<String, BinaryOperator> binaryOperator = new HashMap<String, BinaryOperator>();
    public Deque<BigDecimal> operandStack = new ArrayDeque<BigDecimal>();
    public Deque<BinaryOperator> operatorStack = new ArrayDeque<BinaryOperator>();
    public Deque<Integer> bracketStack = new ArrayDeque<Integer>();

    public CalculatorExpressionStack(){
        binaryOperator.put("+", new PlusOperator(1));
        binaryOperator.put("-", new MinusOperator(1));
        binaryOperator.put("*", new MultiplOperator(2));
        binaryOperator.put("/", new DivideOperator(2));
    }
}
