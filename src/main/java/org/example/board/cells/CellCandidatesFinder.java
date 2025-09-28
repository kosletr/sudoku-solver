package org.example.board.cells;

import org.example.board.SudokuBoard;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CellCandidatesFinder {
    private final SudokuBoard board;

    public CellCandidatesFinder(SudokuBoard board) {
        this.board = Objects.requireNonNull(board);
    }

    public CellCandidates findCellWithTheFewestCandidates() {
        return this.findAllCandidates().entrySet().stream()
                .min(Comparator.comparingInt(a -> a.getValue().length))
                .map(entry -> new CellCandidates(entry.getKey(), entry.getValue()))
                .orElseThrow();
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
