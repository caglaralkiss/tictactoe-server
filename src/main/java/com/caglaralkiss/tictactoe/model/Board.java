package com.caglaralkiss.tictactoe.model;

import com.caglaralkiss.tictactoe.constants.SquareState;
import com.caglaralkiss.tictactoe.util.BoardUtil;

public class Board {
    private Square[][] squares = new Square[3][3];

    public Board() {
        for (int i = 0; i < this.squares.length; i++) {
            for (int j = 0; j < this.squares[i].length; j++) {
                this.squares[i][j] = new Square();
            }
        }
    }

    public Board(Square[][] squares) {
        this.squares = squares;
    }

    public void markSquare(SquareState mark, int i, int j) {
        this.squares[i][j].setSquareState(mark);
    }

    public SquareState checkWinner() {
        return BoardUtil.checkWinner(this);
    }

    public void clearSquare(int i, int j) {
        this.squares[i][j].setSquareState(SquareState.EMPTY_MARK);
    }

    public void clearBoard() {
        for (int i = 0; i < this.squares.length; i++) {
            for (int j = 0; j < this.squares[i].length; j++) {
                this.clearSquare(i, j);
            }
        }
    }

    public boolean isFilled() {
        for (Square[] square : this.squares) {
            for (Square value : square) {
                if (value.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Square[][] getSquares() {
        return this.squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }
}
