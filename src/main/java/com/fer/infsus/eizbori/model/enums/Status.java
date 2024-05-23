package com.fer.infsus.eizbori.model.enums;

public enum Status {
    ONGOING("U tijeku"), FINISHED("Završen");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
