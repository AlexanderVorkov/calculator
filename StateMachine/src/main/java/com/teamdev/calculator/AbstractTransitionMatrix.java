package com.teamdev.calculator;
public interface AbstractTransitionMatrix<States extends Enum> {
    public States getStartState();
    public States[] getTransition(States states);
}
