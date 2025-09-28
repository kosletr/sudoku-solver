import org.example.board.*;
import org.example.board.cells.CellPosition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SudokuBoardTests {

    @Test
    public void parsedBoardIsEqualToExported() {
        final String input = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 7 2 | 1 9 5 | 3 4 8 |
                | 1 9 8 | 3 4 2 | 5 6 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 5 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 1 | 5 3 7 | 2 8 4 |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 4 5 | 2 8 6 | 1 7 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();

        final String result = SudokuBoardParser.toString(board);

        assertThat(input).isEqualTo(result);
    }

    @Test
    public void shouldBeAnInvalidSudokuWhenThereIsASameCellInARow() {
        final String input = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 _ 2 | 1 9 5 | _ 4 8 |
                | 1 9 8 | 3 4 _ | 5 9 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 5 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 1 | 5 3 7 | 2 8 4 |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 4 5 | 2 8 6 | 1 7 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();

        boolean result = board.isValid();

        assertThat(result).isFalse();
    }

    @Test
    public void shouldBeAnInvalidSudokuWhenThereIsASameCellInAColumn() {
        final String input = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 _ 2 | 1 9 5 | _ 4 8 |
                | 1 9 8 | 3 4 2 | 5 6 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 5 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 9 | 5 3 7 | 2 8 4 |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 4 5 | 2 8 6 | 1 7 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();

        boolean result = board.isValid();

        assertThat(result).isFalse();
    }

    @Test
    public void shouldBeAnInvalidSudokuWhenThereIsASameCellInABlock() {
        final String input = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 7 2 | _ _ 5 | 1 4 _ |
                | 1 9 8 | 3 4 2 | 5 6 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 5 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 1 | 5 3 7 | 2 8 4 |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 4 5 | 2 8 6 | 1 7 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();

        boolean result = board.isValid();

        assertThat(result).isFalse();
    }

    @Test
    public void shouldBeAValidSudoku() {
        final String input = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 7 2 | _ _ 5 | _ 4 _ |
                | 1 9 8 | 3 4 2 | 5 6 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 _ 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 1 | 5 3 7 | 2 8 _ |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 _ 5 | 2 8 6 | 1 7 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();

        boolean result = board.isValid();

        assertThat(result).isTrue();
    }

    @Test
    public void shouldBeAnInvalidSudoku() {
        final String input = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 7 2 | _ _ 5 | _ 4 _ |
                | 1 9 8 | 3 4 2 | 5 6 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 _ 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 1 | 5 3 7 | 2 8 _ |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 _ _ | 2 5 6 | 1 7 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();

        boolean result = board.isValid();

        assertThat(result).isFalse();
    }

    @Test
    public void shouldFindBlockStartingPosition() {
        final String input = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 7 2 | _ _ 5 | _ 4 _ |
                | 1 9 8 | 3 4 2 | 5 6 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 _ 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 1 | 5 3 7 | 2 8 _ |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 _ _ | 2 5 6 | 1 7 9 |
                -------------------------
                """;

        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();
        final var cellPosition = new CellPosition(4, 7);

        final CellPosition result = board.findBlockStartingPosition(cellPosition);

        assertThat(new CellPosition(3, 6)).isEqualTo(result);
    }

    @Test
    public void shouldSolveAnEasySudoku() {
        final String input = """
                -------------------------
                | 5 _ _ | _ _ 8 | 9 _ _ |
                | 6 _ _ | _ 9 5 | _ _ _ |
                | _ _ 8 | _ _ 2 | _ 6 7 |
                |-------+-------+-------|
                | _ _ 9 | 7 _ _ | _ 2 _ |
                | 4 _ _ | _ _ 3 | _ _ 1 |
                | 7 1 _ | _ _ 4 | 8 _ 6 |
                |-------+-------+-------|
                | 9 _ 1 | _ 3 _ | _ _ 4 |
                | _ _ 7 | _ 1 _ | _ _ 5 |
                | _ 4 _ | 2 8 _ | _ _ 9 |
                -------------------------
                """;
        final String output = """
                -------------------------
                | 5 3 4 | 6 7 8 | 9 1 2 |
                | 6 7 2 | 1 9 5 | 3 4 8 |
                | 1 9 8 | 3 4 2 | 5 6 7 |
                |-------+-------+-------|
                | 8 5 9 | 7 6 1 | 4 2 3 |
                | 4 2 6 | 8 5 3 | 7 9 1 |
                | 7 1 3 | 9 2 4 | 8 5 6 |
                |-------+-------+-------|
                | 9 6 1 | 5 3 7 | 2 8 4 |
                | 2 8 7 | 4 1 9 | 6 3 5 |
                | 3 4 5 | 2 8 6 | 1 7 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();
        final var sudokuSolver = new Solver(board);

        final SudokuBoard solved = sudokuSolver.solve().orElseThrow();

        assertThat(SudokuBoardParser.toString(solved)).isEqualTo(output);
    }


    @Test
    public void shouldSolveAnExpertSudoku1() {
        final String input = """
                -------------------------
                | _ _ _ | 9 _ 5 | _ 4 _ |
                | _ 9 1 | 8 _ _ | _ _ 2 |
                | 3 _ _ | _ _ _ | _ _ _ |
                |-------+-------+-------|
                | 2 _ _ | 3 _ _ | _ _ _ |
                | 6 _ _ | _ _ _ | _ _ 7 |
                | _ 7 3 | _ _ 8 | 5 _ _ |
                |-------+-------+-------|
                | _ 6 _ | _ _ _ | _ _ _ |
                | _ _ _ | _ 9 _ | 2 _ _ |
                | _ 8 7 | 1 _ _ | _ _ 9 |
                -------------------------
                """;

        final String output = """
                -------------------------
                | 8 2 6 | 9 7 5 | 1 4 3 |
                | 7 9 1 | 8 4 3 | 6 5 2 |
                | 3 4 5 | 6 2 1 | 9 7 8 |
                |-------+-------+-------|
                | 2 5 9 | 3 6 7 | 8 1 4 |
                | 6 1 8 | 4 5 9 | 3 2 7 |
                | 4 7 3 | 2 1 8 | 5 9 6 |
                |-------+-------+-------|
                | 9 6 2 | 5 8 4 | 7 3 1 |
                | 1 3 4 | 7 9 6 | 2 8 5 |
                | 5 8 7 | 1 3 2 | 4 6 9 |
                -------------------------
                """;
        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();
        final var sudokuSolver = new Solver(board);

        final SudokuBoard solved = sudokuSolver.solve().orElseThrow();

        assertThat(SudokuBoardParser.toString(solved)).isEqualTo(output);
    }

    @Test
    public void shouldSolveAnExpertSudoku2() {
        final String input = """
                -------------------------
                | 1 9 _ | _ _ _ | 7 _ _ |
                | 7 _ 5 | _ _ _ | _ 1 8 |
                | 8 _ _ | _ 5 _ | _ _ 6 |
                |-------+-------+-------|
                | _ _ _ | _ _ 4 | 9 6 2 |
                | 4 _ 9 | 3 6 _ | _ _ 5 |
                | _ _ _ | _ 2 _ | _ 7 _ |
                |-------+-------+-------|
                | _ 8 _ | 2 7 6 | _ _ _ |
                | _ 5 _ | _ _ _ | _ _ _ |
                | _ _ 3 | _ 4 _ | _ _ _ |
                -------------------------
                """;
        final String output = """
                -------------------------
                | 1 9 6 | 4 8 2 | 7 5 3 |
                | 7 4 5 | 6 9 3 | 2 1 8 |
                | 8 3 2 | 7 5 1 | 4 9 6 |
                |-------+-------+-------|
                | 3 7 8 | 5 1 4 | 9 6 2 |
                | 4 2 9 | 3 6 7 | 1 8 5 |
                | 5 6 1 | 8 2 9 | 3 7 4 |
                |-------+-------+-------|
                | 9 8 4 | 2 7 6 | 5 3 1 |
                | 2 5 7 | 1 3 8 | 6 4 9 |
                | 6 1 3 | 9 4 5 | 8 2 7 |
                -------------------------
                """;

        final SudokuBoard board = SudokuBoardParser.tryParse(input).orElseThrow();
        final var sudokuSolver = new Solver(board);

        final SudokuBoard solved = sudokuSolver.solve().orElseThrow();

        assertThat(SudokuBoardParser.toString(solved)).isEqualTo(output);
    }
}

