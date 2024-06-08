package com.fer.infsus.eizbori.model;

import com.fer.infsus.eizbori.entity.Election;
import lombok.Getter;

@Getter
public class ElectionInfo {
    private final String name;
    private final String eventDate;
    private final String deadline;

    public ElectionInfo(Election election) {
        this.name = election.getType().getName() + " " + election.getYear();
        this.eventDate = election.getEventDate().toString();
        this.deadline = election.getEventDate().minusDays(7).toString();
    }
}
