package com.caglaralkiss.tictactoe.util;

import com.caglaralkiss.tictactoe.constants.SquareState;
import com.caglaralkiss.tictactoe.model.Board;
import com.caglaralkiss.tictactoe.model.Square;

public class BoardUtil {
    public static SquareState checkWinner(Board board) {
        SquareState diagonalWinnerCheck = diagonalWinnerCheck(board);
        SquareState lineWinnerCheck = lineWinnerCheck(board);

        if (diagonalWinnerCheck != null) {
            return diagonalWinnerCheck;
        } else if (lineWinnerCheck != null) {
            return lineWinnerCheck;
        } else {
            return SquareState.EMPTY_MARK;
        }
    }

    private static SquareState diagonalWinnerCheck(Board board) {
        Square[][] squares = board.getSquares();

        boolean isDashMatched = squares[0][0].getSquareState() == squares[1][1].getSquareState() &&
                squares[1][1].getSquareState() == squares[2][2].getSquareState() &&
                !squares[0][0].isEmpty();

        boolean isSlashMatched = squares[0][2].getSquareState() == squares[1][1].getSquareState() &&
                squares[1][1].getSquareState() == squares[2][0].getSquareState() &&
                !squares[0][2].isEmpty();

        if (isDashMatched) {
            return squares[0][0].getSquareState() == SquareState.X_MARK ?
                    SquareState.X_MARK : SquareState.O_MARK;
        } else if (isSlashMatched){
            return squares[0][2].getSquareState() == SquareState.X_MARK ?
                    SquareState.X_MARK : SquareState.O_MARK;
        } else {
            return null;
        }
    }

    private static SquareState lineWinnerCheck(Board board) {
        Square[][] squares = board.getSquares();

        for (int i = 0; i < squares.length; i++) {
            boolean isHorizontallyMatch = squares[i][0].getSquareState() == squares[i][1].getSquareState()
                    && squares[i][1].getSquareState() == squares[i][2].getSquareState()
                    && !squares[i][0].isEmpty();

            boolean isVerticallyMatch = squares[0][i].getSquareState() == squares[1][i].getSquareState()
                    && squares[1][i].getSquareState() == squares[2][i].getSquareState()
                    && !squares[0][i].isEmpty();

            if (isHorizontallyMatch) {
                return squares[i][0].getSquareState() == SquareState.X_MARK ?
                        SquareState.X_MARK : SquareState.O_MARK;
            } else if (isVerticallyMatch) {
                return squares[0][i].getSquareState() == SquareState.X_MARK ?
                        SquareState.X_MARK : SquareState.O_MARK;
            }
        }

        return null;
    }
}
