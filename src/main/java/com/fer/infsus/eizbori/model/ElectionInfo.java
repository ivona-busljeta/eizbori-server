package com.fer.infsus.eizbori.model;

import com.fer.infsus.eizbori.entity.Election;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ElectionInfo {
    private final Long id;
    private final String name;
    private final LocalDate eventDate;
    private final LocalDate deadline;
    private boolean canApply;

    public ElectionInfo(Election election) {
        this.id = election.getId();
        this.name = election.getType().getName() + " " + election.getYear();
        this.eventDate = election.getEventDate();
        this.deadline = election.getEventDate().minusDays(7);
        this.canApply = !LocalDate.now().isAfter(this.deadline);
    }
}
