package com.fer.infsus.eizbori.dto;

import com.fer.infsus.eizbori.entity.Party;
import lombok.Getter;

@Getter
public class PartyDTO {
    private final long id;
    private final String code;
    private final String name;

    public PartyDTO(Party party) {
        this.id = party.getId();
        this.code = party.getCode();
        this.name = party.getName();
    }
}
