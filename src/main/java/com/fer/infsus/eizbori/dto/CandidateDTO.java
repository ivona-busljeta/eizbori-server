package com.fer.infsus.eizbori.dto;

import com.fer.infsus.eizbori.entity.Candidate;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class CandidateDTO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final long id;
    private final long election;
    private final PersonDTO person;
    private final PartyDTO party;
    private final String applied;

    public CandidateDTO(Candidate candidate) {
        this.id = candidate.getId();
        this.election = candidate.getElection().getId();
        this.person = new PersonDTO(candidate.getPerson());
        this.party = new PartyDTO(candidate.getParty());
        this.applied = formatter.format(candidate.getApplied());
    }
}
