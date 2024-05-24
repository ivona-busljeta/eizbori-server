package com.fer.infsus.eizbori.entity.enums;

import lombok.Getter;

@Getter
public enum Sex {
    MALE('M'), FEMALE('Ž');

    private final char label;

    Sex(char label) {
        this.label = label;
    }

}
