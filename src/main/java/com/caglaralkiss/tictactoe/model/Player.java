package com.caglaralkiss.tictactoe.model;

import com.caglaralkiss.tictactoe.constants.PlayerState;
import com.caglaralkiss.tictactoe.constants.SquareState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    private String id;
    private String name;
    @JsonProperty(value = "mark")
    private SquareState squareState;
    private Integer score;
    @JsonIgnore
    private PlayerState playerState;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player(String id, String name, SquareState squareState, Integer score, PlayerState playerState) {
        this.id = id;
        this.name = name;
        this.squareState = squareState;
        this.score = score;
        this.playerState = playerState;
    }

    public void makeMove(Move move, Board board) throws Exception {
        Square square = board.getSquares()[move.getRow()][move.getColumn()];

        if (square.isEmpty()) {
            board.markSquare(this.squareState, move.getRow(), move.getColumn());
        } else {
            throw new Exception("Mark is not empty!");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SquareState getSquareState() {
        return squareState;
    }

    public void setSquareState(SquareState squareState) {
        this.squareState = squareState;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
