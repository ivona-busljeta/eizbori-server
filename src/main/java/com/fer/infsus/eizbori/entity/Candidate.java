package com.fer.infsus.eizbori.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "candidate", uniqueConstraints = @UniqueConstraint(columnNames = {"election", "person"}))
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Election election;

    @NotNull
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Person person;

    @NotNull
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Position position;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Party party;

    private LocalDate applied = LocalDate.now(ZoneId.of("Europe/Berlin"));
}
