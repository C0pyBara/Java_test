package ru.edu.penzgtu.lab.exception;

import lombok.Getter;

@Getter
public class PenzGtuException extends RuntimeException {
    private final ErrorType type;

    public PenzGtuException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public PenzGtuException(ErrorType type, String message, Throwable throwable) {
        super(message, throwable);
        this.type = type;
    }

    public PenzGtuException(ErrorType type, Throwable throwable) {
        super(throwable);
        this.type = type;
    }
    public PenzGtuException(ErrorType type) {
        super();
        this.type = type;
    }
}