package com.tictactoe.model;

public class Cell {

    private String value;
    private final int row;
    private final int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = "";
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void clear() {
        this.value = "";
    }
}
