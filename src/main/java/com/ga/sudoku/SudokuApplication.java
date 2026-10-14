package com.ga.sudoku;

import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.exception.SudokuFileNotFoundException;

import java.io.IOException;

public class SudokuApplication {

	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();

		try {
			System.out.println("Puzzle 1");
			sudoku.loadPuzzle("puzzle1.txt");
			sudoku.printBoard();

		} catch (SudokuFileNotFoundException
				 | InvalidCharacterException
				 | IOException e) {

			System.err.println("Error: " + e.getMessage());
		}
	}
}
