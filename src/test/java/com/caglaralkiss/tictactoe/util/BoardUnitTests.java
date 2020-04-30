package com.caglaralkiss.tictactoe.util;

import com.caglaralkiss.tictactoe.constants.SquareState;
import com.caglaralkiss.tictactoe.model.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardUnitTests {

    private static final Board board = new Board();

    @BeforeEach
    public void initEach() {
        board.clearBoard();
    }

    @Test
    public void shouldCheckRowWinner() {
        board.markSquare(SquareState.X_MARK, 0, 0);
        board.markSquare(SquareState.X_MARK, 0, 1);
        board.markSquare(SquareState.X_MARK, 0, 2);
        Assertions.assertEquals(SquareState.X_MARK, BoardUtil.checkWinner(board));


        board.clearBoard();
        board.markSquare(SquareState.O_MARK, 1, 0);
        board.markSquare(SquareState.O_MARK, 1, 1);
        board.markSquare(SquareState.O_MARK, 1, 2);
        Assertions.assertEquals(SquareState.O_MARK, BoardUtil.checkWinner(board));


        board.clearBoard();
        board.markSquare(SquareState.X_MARK, 2, 0);
        board.markSquare(SquareState.X_MARK, 2, 1);
        board.markSquare(SquareState.X_MARK, 2, 2);
        Assertions.assertEquals(SquareState.X_MARK, BoardUtil.checkWinner(board));
    }

    @Test
    public void shouldCheckColumnWinner() {
        board.markSquare(SquareState.X_MARK, 0, 0);
        board.markSquare(SquareState.X_MARK, 1, 0);
        board.markSquare(SquareState.X_MARK, 2, 0);
        Assertions.assertEquals(SquareState.X_MARK, BoardUtil.checkWinner(board));


        board.clearBoard();
        board.markSquare(SquareState.O_MARK, 0, 1);
        board.markSquare(SquareState.O_MARK, 1, 1);
        board.markSquare(SquareState.O_MARK, 2, 1);
        Assertions.assertEquals(SquareState.O_MARK, BoardUtil.checkWinner(board));


        board.clearBoard();
        board.markSquare(SquareState.X_MARK, 0, 2);
        board.markSquare(SquareState.X_MARK, 1, 2);
        board.markSquare(SquareState.X_MARK, 2, 2);
        Assertions.assertEquals(SquareState.X_MARK, BoardUtil.checkWinner(board));
    }

    @Test
    public void shouldCheckDiagonalWinners() {
        board.markSquare(SquareState.O_MARK, 0, 0);
        board.markSquare(SquareState.O_MARK, 1, 1);
        board.markSquare(SquareState.O_MARK, 2, 2);
        Assertions.assertEquals(SquareState.O_MARK, BoardUtil.checkWinner(board));

        board.markSquare(SquareState.O_MARK, 2, 0);
        board.markSquare(SquareState.O_MARK, 1, 1);
        board.markSquare(SquareState.O_MARK, 0, 2);
        Assertions.assertEquals(SquareState.O_MARK, BoardUtil.checkWinner(board));
    }

    @Test
    public void shouldCheckNonWinner() {
        board.markSquare(SquareState.X_MARK, 0, 1);
        board.markSquare(SquareState.O_MARK, 1, 1);
        board.markSquare(SquareState.O_MARK, 2, 2);
        Assertions.assertEquals(SquareState.EMPTY_MARK, board.checkWinner());
    }
}
