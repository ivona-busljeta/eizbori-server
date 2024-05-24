package com.fer.infsus.eizbori.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String object, Long id) {
        super("Could not find " + object + " with ID " + id);
    }
}
