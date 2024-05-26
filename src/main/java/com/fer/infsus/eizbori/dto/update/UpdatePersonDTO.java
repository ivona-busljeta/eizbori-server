package com.fer.infsus.eizbori.dto.update;

import com.fer.infsus.eizbori.entity.Person;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
public class UpdatePersonDTO {

    @Getter
    private long id;

    @NotNull
    @Length(max = 200)
    private String firstName;

    @NotNull
    @Length(max = 200)
    private String lastName;

    @NotNull
    @Length(max = 200)
    private String address;

    public Person toPerson() {
        Person person = new Person(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        return person;
    }
}
