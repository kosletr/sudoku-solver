package org.example.board;

import org.example.common.Result;

import java.util.Objects;

public record CellValue(String value) {
    private static final String EMPTY_CELL_VALUE = "_";

    public CellValue {
        Objects.requireNonNull(value);
        if (!CellValue.canCreate(value))
            throw new IllegalArgumentException("Invalid cell value: " + value);
    }

    public static Result<CellValue> tryCreate(String v) {
        return CellValue.canCreate(v)
                ? new Result.Success<>(new CellValue(v))
                : new Result.Error<>("Invalid cell value: " + v);
    }

    private static boolean canCreate(String v) {
        return isEmpty(v) || isBetween1And9(v);
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
