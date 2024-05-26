package com.fer.infsus.eizbori.dto.create;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
public class AddCandidateDTO {

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
        return LocalDate.parse(applied);
    }
}
