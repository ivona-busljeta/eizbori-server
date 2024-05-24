package com.fer.infsus.eizbori.dto;

import com.fer.infsus.eizbori.entity.Position;
import lombok.Getter;

@Getter
public class PositionDTO {
    private final long id;
    private final String code;
    private final String name;

    public PositionDTO(Position position) {
        this.id = position.getId();
        this.code = position.getCode();
        this.name = position.getName();
    }
}
