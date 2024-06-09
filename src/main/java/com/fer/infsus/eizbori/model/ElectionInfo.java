package com.fer.infsus.eizbori.model;

import com.fer.infsus.eizbori.entity.Election;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElectionInfo {
    private final Long id;
    private final String name;
    private final String eventDate;
    private final String deadline;
    private boolean canApply = true;

    public ElectionInfo(Election election) {
        this.id = election.getId();
        this.name = election.getType().getName() + " " + election.getYear();
        this.eventDate = election.getEventDate().toString();
        this.deadline = election.getEventDate().minusDays(7).toString();
    }
}
