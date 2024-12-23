package com.daniil.sockinventorysystem.exceptions;

public class SocksDoesNotExistsException extends RuntimeException {
    public SocksDoesNotExistsException(String message) {
        super(message);
    }
}
