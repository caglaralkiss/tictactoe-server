package com.caglaralkiss.tictactoe.model;

import com.caglaralkiss.tictactoe.constants.SquareState;

public class Square {
    private SquareState squareState;

    public Square() {
        this.squareState = SquareState.EMPTY_MARK;
    }

    public Square(SquareState squareState) {
        this.squareState = squareState;
    }

    public SquareState getSquareState() {
        return squareState;
    }

    public void setSquareState(SquareState squareState) {
        this.squareState = squareState;
    }

    public boolean isEmpty() {
        return this.squareState == SquareState.EMPTY_MARK;
    }
}
