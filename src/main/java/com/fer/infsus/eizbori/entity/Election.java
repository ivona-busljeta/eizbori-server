package com.fer.infsus.eizbori.entity;

import com.fer.infsus.eizbori.config.converters.StatusConverter;
import com.fer.infsus.eizbori.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "election", uniqueConstraints = @UniqueConstraint(columnNames = {"type", "year"}))
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type")
    private ElectionType type;

    private int year = LocalDate.now(ZoneId.of("Europe/Berlin")).getYear();

    private LocalDate eventDate = LocalDate.now(ZoneId.of("Europe/Berlin")).plusDays(90);

    private LocalDate deadline = LocalDate.now(ZoneId.of("Europe/Berlin")).plusDays(14);

    @Convert(converter = StatusConverter.class)
    private Status status = Status.ONGOING;
}
