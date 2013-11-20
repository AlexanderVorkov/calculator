package com.teamdev.consoleclient;

import java.math.BigDecimal;

public class PlusOperator extends AbstractBinaryOperator {

    public PlusOperator(int priority) {
        super(priority);
    }
    //test
    public String getName(){
        return "+";
    }

    public BigDecimal evaluate(BigDecimal first, BigDecimal second) {
        return first.add(second);
    }
}
