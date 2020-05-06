package com.caglaralkiss.tictactoe.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SquareState {
    @JsonProperty("X")
    X_MARK("X"),
    @JsonProperty("O")
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
