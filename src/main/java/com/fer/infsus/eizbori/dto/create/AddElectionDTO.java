package com.fer.infsus.eizbori.dto.create;

import com.fer.infsus.eizbori.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
public class AddElectionDTO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
        return LocalDate.parse(eventDate, formatter);
    }

    public LocalDate getDeadline() {
        return LocalDate.parse(deadline, formatter);
    }

    public Status getStatus() {
        return Status.fromLabel(status);
    }
}
