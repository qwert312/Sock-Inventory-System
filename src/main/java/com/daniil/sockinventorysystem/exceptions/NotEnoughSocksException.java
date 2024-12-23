package com.daniil.sockinventorysystem.exceptions;

public class NotEnoughSocksException extends RuntimeException {
    public NotEnoughSocksException(String message) {
        super(message);
    }
}
