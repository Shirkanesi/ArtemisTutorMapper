package com.shirkanesi.artemistutormapper.exception;

public class ArtemisClientException extends RuntimeException {

    public ArtemisClientException() {
    }

    public ArtemisClientException(String message) {
        super(message);
    }

    public ArtemisClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtemisClientException(Throwable cause) {
        super(cause);
    }

    public ArtemisClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
