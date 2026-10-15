
package com.ga.sudoku.service;

import com.ga.sudoku.Sudoku;
import com.ga.sudoku.SudokuSolver;
import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.model.DTO.response.SudokuSolveResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class SudokuService {

    private static final Path PUZZLES_DIRECTORY =
            Path.of("src", "main", "resources", "puzzles");

    public SudokuSolveResponse solve(MultipartFile file)
            throws IOException, InvalidCharacterException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please upload a non-empty puzzle file."
            );
        }

        String originalName = file.getOriginalFilename();

        if (originalName == null
                || !originalName.toLowerCase().endsWith(".txt")
                || originalName.contains("/")
                || originalName.contains("\\")) {
            throw new IllegalArgumentException(
                    "Please upload a valid .txt file."
            );
        }

        Sudoku sudoku = new Sudoku();
        sudoku.loadPuzzle(file.getInputStream());

        SudokuSolver solver =
                new SudokuSolver(sudoku.getBoard());

        if (!solver.solve()) {
            throw new IllegalArgumentException(
                    "The puzzle has no valid solution."
            );
        }

        String baseName = originalName.substring(
                0, originalName.length() - 4
        );

        String solutionFileName = baseName + ".solution.txt";

        Files.createDirectories(PUZZLES_DIRECTORY);

        Path outputPath =
                PUZZLES_DIRECTORY.resolve(solutionFileName);

        List<List<Integer>> solution = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            List<Integer> values = new ArrayList<>();

            for (int col = 0; col < 9; col++) {
                values.add(
                        sudoku.getBoard()[row][col].getValue()
                );
            }

            solution.add(values);
        }

        StringBuilder content = new StringBuilder();

        for (List<Integer> row : solution) {
            for (int value : row) {
                content.append(value);
            }
            content.append(System.lineSeparator());
        }

        Files.writeString(
                outputPath,
                content.toString(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        return new SudokuSolveResponse(
                "SOLVED",
                solutionFileName,
                solution
        );
    }
}
