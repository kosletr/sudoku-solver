package org.example.board;

import org.example.board.cells.CellValue;
import org.example.common.Result;
import java.util.Arrays;
import java.util.Objects;

public final class SudokuBoardParser {
    private static final String TOP_BOTTOM_BORDER = "-------------------------";
    private static final String     MIDDLE_BORDER = "|-------+-------+-------|";

    private SudokuBoardParser() {}

    public static Result<SudokuBoard> tryParse(String input) {
        final String[] rows = Objects.requireNonNull(input).split("\\R");
        if (rows.length - 4 != 9) return new Result.Error<>("Invalid number of rows: " + rows.length);

        final var cells = new CellValue[9][9];
        int r = 0;
        for (final String row : rows) {
            if (row.equals(TOP_BOTTOM_BORDER) || row.equals(MIDDLE_BORDER)) continue;
            final CellValue[] rowCells = Arrays.stream(row.split("[\\s|]+"))
                    .filter(s -> !s.isEmpty())
                    .map(CellValue::new)
                    .toArray(CellValue[]::new);
            if (rowCells.length != 9)
                return new Result.Error<>("Invalid number of cells: " + rowCells.length + ", in the " + (r + 1) + " row");
            cells[r++] = rowCells;
        }
        final var board = new SudokuBoard(cells);
        return board.isValid()
                ? new Result.Success<>(board)
                : new Result.Error<>("One or more cell(s) are duplicate");
    }

    public static String toString(SudokuBoard board) {
        final CellValue[][] cells = Objects.requireNonNull(board).getCells();
        var output = new StringBuilder(TOP_BOTTOM_BORDER).append("\n");
        for (int r = 0; r < 9; r++) {
            if (r != 0 && r % 3 == 0) output.append(MIDDLE_BORDER).append("\n");
            for (int c = 0; c < 9; c++)
                output.append(c % 3 == 0 ? "| " : "").append(cells[r][c].value()).append(" ");
            output.append("|\n");
        }
        return output.append(TOP_BOTTOM_BORDER).append("\n").toString();
    }
}
