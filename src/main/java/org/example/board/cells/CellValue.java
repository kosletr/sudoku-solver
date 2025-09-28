package org.example.board.cells;

import java.util.Objects;

public record CellValue(String value) {
    private static final String EMPTY_CELL_VALUE = "_";

    public CellValue {
        if (!CellValue.canCreate(Objects.requireNonNull(value)))
            throw new IllegalArgumentException("Invalid cell value: " + value);
    }

    private static boolean canCreate(String v) {
        return CellValue.isEmpty(v) || CellValue.isBetween1And9(v);
    }

    public boolean isEmpty() {
        return CellValue.isEmpty(value);
    }

    private static boolean isEmpty(String v) {
        return EMPTY_CELL_VALUE.equals(v);
    }

    private static boolean isBetween1And9(String v) {
        return v.length() == 1 && v.charAt(0) >= '1' && v.charAt(0) <= '9';
    }
}
