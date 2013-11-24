package com.teamdev.calculator;

import java.math.BigDecimal;

public class DivideOperator extends AbstractBinaryOperator {

    public DivideOperator(int priority) {
        super(priority);
    }
    //test
    public String getName(){
        return "*";
    }
    public BigDecimal evaluate(BigDecimal first, BigDecimal second) throws BinaryOperatorException{
        if (BigDecimal.ZERO.compareTo(first) == 0) {
            throw new BinaryOperatorException("Division by zero.");

        }
        return second.divide(first);
    }
}
