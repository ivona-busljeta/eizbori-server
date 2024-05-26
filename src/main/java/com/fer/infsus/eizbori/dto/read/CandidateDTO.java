package com.fer.infsus.eizbori.dto.read;

import com.fer.infsus.eizbori.entity.Candidate;
import lombok.Getter;

@Getter
public class CandidateDTO {
    private final long id;
    private final long election;
    private final PersonDTO person;
    private final PartyDTO party;
    private final String applied;

    public CandidateDTO(Candidate candidate) {
        this.id = candidate.getId();
        this.election = candidate.getElection().getId();
        this.person = new PersonDTO(candidate.getPerson());
        if (candidate.getParty() != null) {
            this.party = new PartyDTO(candidate.getParty());
        } else {
            this.party = null;
        }
        this.applied = candidate.getApplied().toString();
    }
}
