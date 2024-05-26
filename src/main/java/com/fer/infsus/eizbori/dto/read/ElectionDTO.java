package com.fer.infsus.eizbori.dto.read;

import com.fer.infsus.eizbori.entity.Election;
import lombok.Getter;

@Getter
public class ElectionDTO {
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
       this.eventDate = election.getEventDate().toString();
       this.deadline = election.getDeadline().toString();
       this.status = election.getStatus().getLabel();
   }
}
