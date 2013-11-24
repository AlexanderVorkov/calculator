package com.teamdev.calculator;

import java.math.BigDecimal;

public class MinusOperator extends AbstractBinaryOperator {
    public MinusOperator(int priority) {
        super(priority);
    }
    //test
    public String getName(){
        return "-";
    }
    public BigDecimal evaluate(BigDecimal first, BigDecimal second) throws BinaryOperatorException{
        return second.subtract(first);
    }
}
