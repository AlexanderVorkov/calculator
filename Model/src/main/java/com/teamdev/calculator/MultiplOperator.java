package com.teamdev.calculator;

import java.math.BigDecimal;

public class MultiplOperator extends AbstractBinaryOperator {

    public MultiplOperator(int priority) {
        super(priority);
    }
    //test
    public String getName(){
        return "*";
    }
    public BigDecimal evaluate(BigDecimal first, BigDecimal second) throws BinaryOperatorException{
        return first.multiply(second);
    }
}
