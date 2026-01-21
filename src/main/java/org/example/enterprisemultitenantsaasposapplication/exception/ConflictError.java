package org.example.enterprisemultitenantsaasposapplication.exception;

public class ConflictError extends RuntimeException {
    public ConflictError(String message) {
        super(message);
    }
}
