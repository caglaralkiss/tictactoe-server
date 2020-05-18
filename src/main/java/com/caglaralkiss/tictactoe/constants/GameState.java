package com.caglaralkiss.tictactoe.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public enum GameState {
    PLAYING,
    @JsonProperty(value = "X")
    X_WIN,
    @JsonProperty(value = "O")
    O_WIN,
    @JsonProperty(value = "_")
    DRAW
}
