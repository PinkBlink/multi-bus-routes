package org.multi.routes.exception;

import java.io.IOException;

public class NoFileException extends IOException {
    public NoFileException(String message) {
        super(message);
    }

    public NoFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFileException(Throwable cause) {
        super(cause);
    }
}
