package com.caglaralkiss.tictactoe.model;

import com.caglaralkiss.tictactoe.constants.GameState;
import com.caglaralkiss.tictactoe.constants.Turn;

public class Game {

    private Board board;
    private GameState gameState;
    private Player player1;
    private Player player2;
    private Turn turn;

    public Game() {
        this.board = new Board();
    }

    public Game(Board board, GameState gameState, Player player1, Player player2, Turn turn) {
        this.board = board;
        this.gameState = gameState;
        this.player1 = player1;
        this.player2 = player2;
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
