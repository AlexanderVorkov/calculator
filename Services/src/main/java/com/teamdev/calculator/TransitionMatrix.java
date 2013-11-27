package com.teamdev.calculator;

import java.util.HashMap;

public class TransitionMatrix implements AbstractTransitionMatrix<States> {
    private HashMap<States, States[]> matrix = new HashMap<States, States[]>();
    public TransitionMatrix(){
        matrix.put(States.START, new States[]{States.NUMBER, States.LEFT_BRACKET});
        matrix.put(States.NUMBER, new States[]{States.BINARY_OPERATOR, States.RIGHT_BRACKET, States.FINISH});
        matrix.put(States.BINARY_OPERATOR, new States[]{States.NUMBER,States.LEFT_BRACKET});
        matrix.put(States.LEFT_BRACKET, new States[]{States.NUMBER,States.LEFT_BRACKET});
        matrix.put(States.RIGHT_BRACKET, new States[]{States.BINARY_OPERATOR, States.RIGHT_BRACKET, States.FINISH});
        matrix.put(States.FINISH, new States[]{});
    }

    @Override
    public States getStartState(){
        return States.START;
    }

    @Override
    public States[] getTransition(States state){
        return matrix.get(state);
    }
}

