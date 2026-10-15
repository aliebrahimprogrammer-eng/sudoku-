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

			SudokuSolver solver =
					new SudokuSolver(sudoku.getBoard());

			if (solver.solve()) {
				System.out.println("\nPuzzle 1 Solved");
				sudoku.printBoard();
			} else {
				System.out.println(
						"The puzzle has no valid solution."
				);
			}
		} catch (SudokuFileNotFoundException
				 | InvalidCharacterException
				 | IOException e) {

			System.err.println("Error: " + e.getMessage());
		}
	}
}