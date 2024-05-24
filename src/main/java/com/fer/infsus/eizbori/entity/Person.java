package com.fer.infsus.eizbori.entity;

import com.fer.infsus.eizbori.entity.enums.Sex;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Length(max = 200)
    private String firstName;

    @NotNull
    @Length(max = 200)
    private String lastName;

    @NotNull
    private LocalDate dob;

    @NotNull
    @Length(max = 200)
    private String address;

    @NotNull
    @Pattern(regexp = "[1-9][0-9]{10}")
    @Column(unique = true)
    private String pid;

    @NotNull
    @Length(max = 200)
    private String nationality;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Sex sex;

    public Person() {

    }

    public Person(long id) {
        this.id = id;
    }
}
