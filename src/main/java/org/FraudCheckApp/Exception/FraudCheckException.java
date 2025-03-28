package org.FraudCheckApp.Exception;


public class FraudCheckException extends RuntimeException {
    public FraudCheckException(String message) {
        super(message);
    }

    public FraudCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}