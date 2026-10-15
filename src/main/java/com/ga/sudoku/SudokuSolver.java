
package com.ga.sudoku;

import com.ga.sudoku.model.SudokuCell;

public class SudokuSolver {

    private final SudokuCell[][] board;

    public SudokuSolver(SudokuCell[][] board) {
        this.board = board;
    }

    public boolean solve() {
        if (!isInitialBoardValid()) {
            return false;
        }

        return solveBoard();
    }

    private boolean solveBoard() {

        // Find an empty cell.
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col].isEmpty()) {

                    // Try every number from 1 to 9.
                    for (int number = 1; number <= 9; number++) {

                        if (isSafe(row, col, number)) {

                            // Make a choice.
                            board[row][col].setValue(number);

                            // Continue solving.
                            if (solveBoard()) {
                                return true;
                            }

                            // Backtrack: undo the choice.
                            board[row][col].setValue(0);
                        }
                    }

                    // No number works for this cell.
                    return false;
                }
            }
        }

        // No empty cells remain.
        return true;
    }

    private boolean isSafe(int row, int col, int number) {

        // Check the row.
        for (int c = 0; c < 9; c++) {
            if (board[row][c].getValue() == number) {
                return false;
            }
        }

        // Check the column.
        for (int r = 0; r < 9; r++) {
            if (board[r][col].getValue() == number) {
                return false;
            }
        }

        // Find the top-left corner of the 3x3 box.
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        // Check the 3x3 box.
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c].getValue() == number) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isInitialBoardValid() {

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                int value = board[row][col].getValue();

                if (value < 0 || value > 9) {
                    return false;
                }

                if (value != 0) {
                    // Temporarily remove the clue.
                    board[row][col].setValue(0);

                    boolean valid = isSafe(row, col, value);

                    // Restore the original clue.
                    board[row][col].setValue(value);

                    if (!valid) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
