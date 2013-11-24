package com.teamdev.calculator;

import java.math.BigDecimal;

public interface BinaryOperator extends Comparable<BinaryOperator>{
    public BigDecimal evaluate(BigDecimal first, BigDecimal second) throws BinaryOperatorException;
    public int getPriority();
    public String getName();
}
