package com.fer.infsus.eizbori.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "election_type")
public class ElectionType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Length(max = 10)
    @Column(unique = true)
    private String code;

    @NotNull
    @Length(max = 200)
    private String name;
}
