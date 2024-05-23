package com.fer.infsus.eizbori.model;

import com.fer.infsus.eizbori.model.enums.Status;
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
    @JoinColumn(referencedColumnName = "id")
    private ElectionType type;

    private int year = LocalDate.now(ZoneId.of("Europe/Berlin")).getYear();

    private LocalDate eventDate = LocalDate.now(ZoneId.of("Europe/Berlin")).plusDays(90);

    private LocalDate deadline = LocalDate.now(ZoneId.of("Europe/Berlin")).plusDays(14);

    @Enumerated(EnumType.STRING)
    private Status status = Status.ONGOING;
}
