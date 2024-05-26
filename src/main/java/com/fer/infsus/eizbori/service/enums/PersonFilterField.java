package com.fer.infsus.eizbori.service.enums;

import lombok.Getter;

@Getter
public enum PersonFilterField {
    FIRST_NAME("firstName"), LAST_NAME("lastName"), PID("pid");

    private final String label;

    PersonFilterField(String label) {
        this.label = label;
    }

    public static PersonFilterField fromLabel(String label) {
        if (label == null) return null;
        return switch (label) {
            case "firstName" -> FIRST_NAME;
            case "lastName" -> LAST_NAME;
            case "pid" -> PID;
            default -> throw new IllegalArgumentException("No enum label : " + label);
        };
    }
}
