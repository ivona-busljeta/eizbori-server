package com.fer.infsus.eizbori.dto.read;

import com.fer.infsus.eizbori.entity.Election;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ElectionDTO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final long id;
    private final ElectionTypeDTO type;
    private final int year;
    private final String eventDate;
    private final String deadline;
    private final String status;

   public ElectionDTO(Election election) {
       this.id = election.getId();
       this.type = new ElectionTypeDTO(election.getType());
       this.year = election.getYear();
       this.eventDate = formatter.format(election.getEventDate());
       this.deadline = formatter.format(election.getDeadline());
       this.status = election.getStatus().getLabel();
   }
}
