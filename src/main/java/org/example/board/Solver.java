package org.example.board;

import org.example.common.Result;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solver {
    private final SudokuBoard board;

    public Solver(SudokuBoard board) {
        Objects.requireNonNull(board);
        this.board = board;
    }

    public Result<SudokuBoard> solve() {
        var attempt = solveRecursively(this.board);
        return attempt.isPossible()
                ? new Result.Success<>(attempt.sudokuBoard())
                : new Result.Error<>("No solution");
    }

    private record NextStep(SudokuBoard sudokuBoard, boolean isPossible) {}

    private static NextStep solveRecursively(SudokuBoard currentBoard) {
        if (currentBoard.isSolved())
            return new NextStep(currentBoard, true);

        final var minPossibleCandidates = new Solver(currentBoard).findAllCandidates().entrySet().stream()
                .min(Comparator.comparingInt(a -> a.getValue().length))
                .orElseThrow();

        final CellPosition position = minPossibleCandidates.getKey();
        final CellValue[] possibleValues = minPossibleCandidates.getValue();

        if (possibleValues.length == 0)
            System.out.println("Impossible to change: " + position);

        for (CellValue value : possibleValues) {
            System.out.println("Trying cell " + value + " at " + position);
            final NextStep nextStep = Solver.solveRecursively(currentBoard.withChanged(position, value));
            if (nextStep.isPossible) return nextStep;
            System.out.println("Reverting cell " + value + " at " + position);
        }
        return new NextStep(currentBoard, false);
    }

    private Map<CellPosition, CellValue[]> findAllCandidates() {
        final Map<CellPosition, CellValue[]> allCandidates = new HashMap<>();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                final var position = new CellPosition(r, c);
                if (this.board.at(position).isEmpty())
                    allCandidates.put(position, this.findCellCandidates(position));
            }
        return allCandidates;
    }

    private CellValue[] findCellCandidates(CellPosition position) {
        if (!this.board.at(position).isEmpty())
            return new CellValue[] {};

        final Set<CellValue> candidates = IntStream.range(1, 10)
                .mapToObj(c -> new CellValue(String.valueOf(c)))
                .collect(Collectors.toSet());

        candidates.removeIf(this.board.getRow(position.row())::contains);
        candidates.removeIf(this.board.getColumn(position.column())::contains);
        candidates.removeIf(this.board.getBlock(this.board.findBlockStartingPosition(position))::contains);

        return candidates.toArray(CellValue[]::new);
    }
}
