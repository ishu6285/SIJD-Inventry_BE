package com.sijd.ims.exception;

public class SijdException extends RuntimeException {

    public SijdException() {
        super();
    }

    public SijdException(String message) {
        super(message);
    }

    public SijdException(String message, Throwable cause) {
        super(message, cause);
    }

    // You can add additional factory methods if desired
    public static SijdException notFound(String resource) {
        return new SijdException(resource + " not found");
    }

    public static SijdException badRequest(String message) {
        return new SijdException(message);
    }

    public static SijdException duplicateEntry(String entity) {
        return new SijdException("Duplicate entry for " + entity);
    }
}