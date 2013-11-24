package com.teamdev.calculator;

import java.math.BigDecimal;

abstract class AbstractBinaryOperator implements BinaryOperator {
    private int priority;

    public AbstractBinaryOperator(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(BinaryOperator o) {
        return priority < o.getPriority() ? 1 : 0;
    }
}

