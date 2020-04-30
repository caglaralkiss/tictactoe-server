package com.caglaralkiss.tictactoe.model;

import com.caglaralkiss.tictactoe.constants.PlayerState;
import com.caglaralkiss.tictactoe.constants.SquareState;

public class Player {

    private String name;
    private SquareState squareState;
    private Integer score;
    private PlayerState playerState;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, SquareState squareState, Integer score, PlayerState playerState) {
        this.name = name;
        this.squareState = squareState;
        this.score = score;
        this.playerState = playerState;
    }

    public void makeMove(int row, int column, Board board) throws Exception {
        Square square = board.getSquares()[row][column];

        if (square.isEmpty()) {
            board.markSquare(this.squareState, row, column);
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
}
