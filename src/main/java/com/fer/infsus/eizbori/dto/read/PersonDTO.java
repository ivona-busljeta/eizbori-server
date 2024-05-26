package com.fer.infsus.eizbori.dto.read;

import com.fer.infsus.eizbori.entity.Person;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PersonDTO {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final long id;
    private final String firstName;
    private final String lastName;
    private final String dob;
    private final String address;
    private final String pid;
    private final String nationality;
    private final String sex;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.dob = formatter.format(person.getDob());
        this.address = person.getAddress();
        this.pid = person.getPid();
        this.nationality = person.getNationality();
        this.sex = person.getSex().getLabel();
    }
}
