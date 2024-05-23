package com.fer.infsus.eizbori.model.enums;

public enum Sex {
    MALE('M'), FEMALE('Ž');

    private final char label;

    Sex(char label) {
        this.label = label;
    }

    public char getLabel() {
        return label;
    }
}
