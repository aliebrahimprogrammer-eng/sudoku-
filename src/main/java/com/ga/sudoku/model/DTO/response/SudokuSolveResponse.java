
package com.ga.sudoku.model.DTO.response;

import java.util.List;

public class SudokuSolveResponse {

    private final String status;
    private final String solutionFile;
    private final List<List<Integer>> solution;

    public SudokuSolveResponse(
            String status,
            String solutionFile,
            List<List<Integer>> solution) {

        this.status = status;
        this.solutionFile = solutionFile;
        this.solution = solution;
    }

    public String getStatus() {
        return status;
    }

    public String getSolutionFile() {
        return solutionFile;
    }

    public List<List<Integer>> getSolution() {
        return solution;
    }
}
