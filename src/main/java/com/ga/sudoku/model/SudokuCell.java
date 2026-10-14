package com.ga.sudoku.model;

public class SudokuCell {

    private int value;

    public SudokuCell(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == 0;
    }
}
