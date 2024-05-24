package com.fer.infsus.eizbori.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
public class AddCandidateDTO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Getter
    private long electionId;

    @Getter
    private long personId;

    @Getter
    private long positionId;

    @Getter
    private Long partyId;

    @NotNull
    private String applied;

    public LocalDate getApplicationDate() {
        return LocalDate.parse(applied, formatter);
    }
}
