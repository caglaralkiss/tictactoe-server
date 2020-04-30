package com.caglaralkiss.tictactoe.constants;

public enum SquareState {
    X_MARK("X"),
    O_MARK("O"),
    EMPTY_MARK("EMPTY");

    private final String mark;

    SquareState(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }
}
