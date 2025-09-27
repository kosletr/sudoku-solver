package org.example.common;

public sealed interface Result<T> {
    T orElseThrow();

    record Success<T>(T value) implements Result<T> {
        @Override
        public T orElseThrow() {
            return value;
        }
    }
    record Error<T>(String message) implements Result<T> {
        @Override
        public T orElseThrow() {
            throw new IllegalArgumentException(message);
        }
    }
}