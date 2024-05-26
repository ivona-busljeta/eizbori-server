package com.fer.infsus.eizbori.dto.create;

import com.fer.infsus.eizbori.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
public class AddElectionDTO {

    @Getter
    private long typeId;

    @Getter
    private int year;

    @NotNull
    private String eventDate;

    @NotNull
    private String deadline;

    @NotNull
    private String status;

    public LocalDate getEventDate() {
        return LocalDate.parse(eventDate);
    }

    public LocalDate getDeadline() {
        return LocalDate.parse(deadline);
    }

    public Status getStatus() {
        return Status.valueOf(status);
    }
}
