package com.fer.infsus.eizbori.dto.read;

import com.fer.infsus.eizbori.entity.ElectionType;
import lombok.Getter;

@Getter
public class ElectionTypeDTO {
    private final long id;
    private final String code;
    private final String name;

    public ElectionTypeDTO(ElectionType electionType) {
        this.id = electionType.getId();
        this.code = electionType.getCode();
        this.name = electionType.getName();
    }
}
