package com.fer.infsus.eizbori.entity.enums;

import lombok.Getter;

@Getter
public enum Status {
    ONGOING("U tijeku"), FINISHED("Završen");

    private final String label;

    Status(String label) {
        this.label = label;
    }

}
