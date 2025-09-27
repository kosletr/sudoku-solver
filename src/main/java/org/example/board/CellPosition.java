package org.example.board;

public record CellPosition(int row, int column) {
    public CellPosition {
        if (row < 0 || row > 8)
            throw new IllegalArgumentException("Row cannot be: " + row);
        if (column < 0 || column > 8)
            throw new IllegalArgumentException("Column cannot be: " + column);
    }
}
