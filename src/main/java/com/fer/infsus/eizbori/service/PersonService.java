package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.dto.AddPersonDTO;
import com.fer.infsus.eizbori.dto.PersonDTO;
import com.fer.infsus.eizbori.dto.UpdatePersonDTO;
import com.fer.infsus.eizbori.exception.ObjectNotFoundException;
import com.fer.infsus.eizbori.entity.Person;
import com.fer.infsus.eizbori.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO savePerson(AddPersonDTO addPersonDTO) {
        return new PersonDTO(personRepository.save(addPersonDTO.toPerson()));
    }

    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll().stream().map(PersonDTO::new).toList();
    }

    public PersonDTO getPersonById(long id) {
        return new PersonDTO(fetchPerson(id));
    }

    public PersonDTO updatePerson(UpdatePersonDTO updatePersonDTO) {
        boolean isPresent = personRepository.existsById(updatePersonDTO.getId());
        if (isPresent) {
            return new PersonDTO(personRepository.save(updatePersonDTO.toPerson()));
        }
        throw new ObjectNotFoundException("person", updatePersonDTO.getId());
    }

    public void deletePersonById(long id) {
        personRepository.deleteById(id);
    }

    protected Person fetchPerson(long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return person.get();
        }
        throw new ObjectNotFoundException("person", id);
    }
}
