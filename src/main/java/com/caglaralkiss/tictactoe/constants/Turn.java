package com.caglaralkiss.tictactoe.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Turn {
    @JsonProperty(value = "X")
    X_TURN,
    @JsonProperty(value = "O")
    O_TURN
}
