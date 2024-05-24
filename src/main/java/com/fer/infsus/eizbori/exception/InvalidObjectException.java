package com.fer.infsus.eizbori.exception;

public class InvalidObjectException extends RuntimeException {

    public InvalidObjectException(String object) {
        super("Could not save " + object);
    }
}
