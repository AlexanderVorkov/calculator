package com.teamdev.consoleclient;

import java.math.BigDecimal;

public class DivideOperator extends AbstractBinaryOperator {

    public DivideOperator(int priority) {
        super(priority);
    }
    //test
    public String getName(){
        return "*";
    }
    public BigDecimal evaluate(BigDecimal first, BigDecimal second) {
        return second.divide(first);
    }
}
