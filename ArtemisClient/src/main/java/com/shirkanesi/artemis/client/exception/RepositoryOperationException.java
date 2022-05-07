package com.shirkanesi.artemis.client.exception;

import com.shirkanesi.artemis.client.logic.ArtemisClient;

public class RepositoryOperationException extends ArtemisClientException {

    public RepositoryOperationException() {
    }

    public RepositoryOperationException(String message) {
        super(message);
    }

    public RepositoryOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryOperationException(Throwable cause) {
        super(cause);
    }

    public RepositoryOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
