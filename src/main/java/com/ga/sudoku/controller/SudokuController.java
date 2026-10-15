
package com.ga.sudoku.controller;

import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.model.DTO.response.ErrorResponse;
import com.ga.sudoku.model.DTO.response.SudokuSolveResponse;
import com.ga.sudoku.service.SudokuService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/sudoku")
public class SudokuController {

    private final SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @PostMapping(
            value = "/solve",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> solve(
            @RequestParam("file") MultipartFile file) {

        try {
            SudokuSolveResponse response =
                    sudokuService.solve(file);

            return ResponseEntity.ok(response);

        } catch (InvalidCharacterException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));

        } catch (IOException e) {
            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(new ErrorResponse(
                    "Unable to read or save the puzzle."
            ));
        }
    }
}
