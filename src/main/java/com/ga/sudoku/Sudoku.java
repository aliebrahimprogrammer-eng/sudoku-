package com.ga.sudoku;

import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.exception.SudokuFileNotFoundException;
import com.ga.sudoku.model.SudokuCell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Sudoku {

    private final SudokuCell[][] board = new SudokuCell[9][9];

    public void loadPuzzle(String fileName)
            throws SudokuFileNotFoundException,
            InvalidCharacterException,
            IOException {

        String path = "/puzzles/" + fileName;

        InputStream input = getClass().getResourceAsStream(path);

        if (input == null) {
            throw new SudokuFileNotFoundException(
                    "Puzzle file not found: " + fileName
            );
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {

            for (int row = 0; row < 9; row++) {
                String line = reader.readLine();

                if (line == null || line.length() != 9) {
                    throw new IllegalArgumentException(
                            "Each puzzle row must contain exactly 9 digits."
                    );
                }

                for (int col = 0; col < 9; col++) {
                    char character = line.charAt(col);

                    if (character < '0' || character > '9') {
                        throw new InvalidCharacterException(
                                "Invalid character '" + character
                                        + "' at row " + (row + 1)
                                        + ", column " + (col + 1)
                        );
                    }

                    int value = character - '0';
                    board[row][col] = new SudokuCell(value);
                }
            }

            if (reader.readLine() != null) {
                throw new IllegalArgumentException(
                        "The puzzle must contain exactly 9 rows."
                );
            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < 9; row++) {

            if (row == 3 || row == 6) {
                System.out.println("----------------------------------");
                System.out.println();
            }

            for (int col = 0; col < 9; col++) {

                if (col == 3 || col == 6) {
                    System.out.print("| ");
                }

                System.out.print(board[row][col].getValue() + "  ");
            }

            System.out.println();
        }
    }

    public SudokuCell[][] getBoard() {
        return board;
    }
}
