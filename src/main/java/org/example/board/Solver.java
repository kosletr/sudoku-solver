package org.example.board;

import org.example.board.cells.CellCandidates;
import org.example.board.cells.CellCandidatesFinder;
import org.example.board.cells.CellValue;
import org.example.common.Result;
import java.util.Objects;

public class Solver {
    private final SudokuBoard board;

    public Solver(SudokuBoard board) {
        this.board = Objects.requireNonNull(board);
    }

    public Result<SudokuBoard> solve() {
        final BoardState state = solveRecursively(this.board);
        return state.isSolvable()
                ? new Result.Success<>(state.sudokuBoard())
                : new Result.Error<>("No solution");
    }

    private record BoardState(SudokuBoard sudokuBoard, boolean isSolvable) {}

    private static BoardState solveRecursively(SudokuBoard currentBoard) {
        if (currentBoard.isSolved())
            return new BoardState(currentBoard, true);

        final CellCandidates cell = new CellCandidatesFinder(currentBoard).findCellWithTheFewestCandidates();

        if (cell.values().length == 0)
            System.out.println("Impossible to change: " + cell.position());

        for (CellValue value : cell.values()) {
            System.out.println("Trying cell " + value + " at " + cell.position());
            final BoardState newState = Solver.solveRecursively(currentBoard.withChanged(cell.position(), value));
            if (newState.isSolvable) return newState;
            System.out.println("Reverting cell " + value + " at " + cell.position());
        }
        return new BoardState(currentBoard, false);
    }
}
