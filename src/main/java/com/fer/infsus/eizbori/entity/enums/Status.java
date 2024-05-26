package com.fer.infsus.eizbori.entity.enums;

import lombok.Getter;

@Getter
public enum Status {
    ONGOING("U tijeku"), FINISHED("Završen");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public static Status fromLabel(String label) {
        return switch (label) {
            case "U tijeku" -> ONGOING;
            case "Završen" -> FINISHED;
            default -> throw new IllegalArgumentException("No enum label: " + label);
        };
    }
}
