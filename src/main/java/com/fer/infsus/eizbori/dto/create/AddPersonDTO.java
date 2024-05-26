package com.fer.infsus.eizbori.dto.create;

import com.fer.infsus.eizbori.entity.Person;
import com.fer.infsus.eizbori.entity.enums.Sex;
import com.fer.infsus.eizbori.exception.InvalidObjectException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Setter
public class AddPersonDTO {

    @NotNull
    @Length(max = 200)
    private String firstName;

    @NotNull
    @Length(max = 200)
    private String lastName;

    @NotNull
    private String dob;

    @NotNull
    @Length(max = 200)
    private String address;

    @NotNull
    @Pattern(regexp = "[1-9][0-9]{10}")
    private String pid;

    @NotNull
    @Length(max = 200)
    private String nationality;

    @NotNull
    private String sex;

    public Person toPerson() {
        try {
            Person person = new Person();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setDob(LocalDate.parse(dob));
            person.setAddress(address);
            person.setPid(pid);
            person.setNationality(nationality);
            person.setSex(Sex.valueOf(sex));
            return person;
        } catch (Exception e) {
            throw new InvalidObjectException("person");
        }
    }
}
