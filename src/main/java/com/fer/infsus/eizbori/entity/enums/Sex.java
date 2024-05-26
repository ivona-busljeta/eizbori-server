package com.fer.infsus.eizbori.entity.enums;

import lombok.Getter;

@Getter
public enum Sex {
    MALE("M"), FEMALE("Ž");

    private final String label;

    Sex(String label) {
        this.label = label;
    }

    public static Sex fromLabel(String label) {
         return switch (label) {
             case "M" -> MALE;
             case "Ž" -> FEMALE;
             default -> throw new IllegalStateException("No enum label: " + label);
         };
    }
}
