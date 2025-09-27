package org.example.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class SudokuBoard {
    private final CellValue[][] cells;

    SudokuBoard(CellValue[][] cells) {
        Objects.requireNonNull(cells);
        if (!this.is9by9(cells))
            throw new IllegalArgumentException("Sudoku board dimensions must be 9x9");
        this.cells = cells;
    }

    private SudokuBoard(SudokuBoard other) {
        var cells = new CellValue[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                cells[r][c] = other.at(new CellPosition(r, c));
        this.cells = cells;
    }

    private boolean is9by9(CellValue[][] cells) {
        return cells.length == 9 && Arrays.stream(cells).allMatch(row -> row != null && row.length == 9);
    }

    public SudokuBoard withChanged(CellPosition position, CellValue newValue) {
        SudokuBoard newBoard = new SudokuBoard(this);
        newBoard.cells[position.row()][position.column()] = newValue;
        return newBoard;
    }

    public CellValue at(CellPosition position) {
        return this.cells[position.row()][position.column()];
    }

    public boolean isValid() {
        for (int i = 0; i < 9; i++)
            if (this.rowHasDuplicates(i) || this.columnHasDuplicates(i))
                return false;
        for (int r = 0; r < 9; r+=3)
            for (int c = 0; c < 9; c+=3)
                if (this.blockHasDuplicates(new CellPosition(r, c)))
                    return false;
        return true;
    }


    public List<CellValue> getRow(int row) {
        return Arrays.asList(this.cells[row]);
    }

    public List<CellValue> getColumn(int col) {
        final List<CellValue> column = new ArrayList<>();
        for (int r = 0; r < 9; r++)
            column.add(this.cells[r][col]);
        return column;
    }

    public List<CellValue> getBlock(CellPosition startingPosition) {
        final List<CellValue> block = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            block.add(this.cells[startingPosition.row() + i / 3][startingPosition.column() + i % 3]);
        return block;
    }

    private boolean rowHasDuplicates(int row) {
        return this.hasDuplicates(this.getRow(row));
    }

    private boolean columnHasDuplicates(int col) {
        return this.hasDuplicates(this.getColumn(col));
    }

    private boolean blockHasDuplicates(CellPosition startingPosition) {
        return this.hasDuplicates(this.getBlock(startingPosition));
    }

    private boolean hasDuplicates(List<CellValue> cells) {
        return cells.stream().filter(c -> !c.isEmpty()).distinct().count() !=
                cells.stream().filter(c -> !c.isEmpty()).count();
    }

    public CellPosition findBlockStartingPosition(CellPosition cellPosition) {
        return new CellPosition(
                3 * (cellPosition.row() / 3),
                3 * (cellPosition.column() / 3));
    }

    public boolean isSolved() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (this.at(new CellPosition(r, c)).isEmpty())
                    return false;
        return true;
    }

    public CellValue[][] getCells() {
        return new SudokuBoard(this).cells;
    }
}
