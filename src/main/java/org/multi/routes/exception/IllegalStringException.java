package org.multi.routes.exception;

public class IllegalStringException extends IllegalArgumentException {
    public IllegalStringException() {
        super();
    }

    public IllegalStringException(String s) {
        super(s);
    }

    public IllegalStringException(String message, Throwable cause) {
        super(message, cause);
    }
}