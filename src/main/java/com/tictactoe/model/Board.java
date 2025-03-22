package com.tictactoe.model;

import java.util.UUID;

public class Board {

    private final Cell[][] cells;
    private final int size;
    private final String id;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.id = UUID.randomUUID().toString();

        // Initialize cells
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (isValidPosition(row, col)) {
            return cells[row][col];
        }
        return null;
    }

    public boolean placeSymbol(int row, int col, String symbol) {
        if (isValidPosition(row, col) && cells[row][col].isEmpty()) {
            cells[row][col].setValue(symbol);
            return true;
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j].clear();
            }
        }
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public String getId() {
        return id;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
