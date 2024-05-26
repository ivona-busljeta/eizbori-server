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
    @JoinColumn(name = "election")
    private Election election;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "position")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "party")
    private Party party;

    private LocalDate applied = LocalDate.now(ZoneId.of("Europe/Berlin"));
}
