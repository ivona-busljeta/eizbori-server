package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.dto.create.AddPersonDTO;
import com.fer.infsus.eizbori.dto.read.PageDTO;
import com.fer.infsus.eizbori.dto.read.PersonDTO;
import com.fer.infsus.eizbori.dto.update.UpdatePersonDTO;
import com.fer.infsus.eizbori.exception.ObjectNotFoundException;
import com.fer.infsus.eizbori.entity.Person;
import com.fer.infsus.eizbori.repository.PersonRepository;
import com.fer.infsus.eizbori.service.enums.PersonFilterField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonService {

    private final Sort defaultSort = Sort.by(Sort.Direction.ASC, "pid");

    private final PersonRepository personRepository;
    private final PagingService pagingService;

    @Autowired
    public PersonService(PersonRepository personRepository, PagingService pagingService) {
        this.personRepository = personRepository;
        this.pagingService = pagingService;
    }

    public PersonDTO savePerson(AddPersonDTO addPersonDTO) {
        return new PersonDTO(personRepository.save(addPersonDTO.toPerson()));
    }

    public PageDTO<PersonDTO> getPersonsPage(PersonFilterField field, String term, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, defaultSort);
        Page<Person> persons;
        if (field == null || term == null || term.isBlank()) {
            persons = personRepository.findAll(pageable);
        } else {
            switch (field) {
                case FIRST_NAME -> persons = personRepository.findByFirstNameContainsIgnoreCase(term, pageable);
                case LAST_NAME -> persons = personRepository.findByLastNameContainsIgnoreCase(term, pageable);
                case PID -> persons = personRepository.findByPidContains(term, pageable);
                default -> persons = personRepository.findAll(pageable);
            }
        }
        List<PersonDTO> pageItems = persons.getContent().stream().map(PersonDTO::new).toList();
        return new PageDTO<>(pageItems, page, size, persons.getTotalElements());
    }

    public PageDTO<PersonDTO> getPageContainingPerson(int id, int size) {
        Person person = fetchPerson(id);
        List<Person> persons = personRepository.findAll(defaultSort);

        Map<String, Object> pageConfig = pagingService.getPageConfig(person, persons, size);
        Integer page = (Integer) pageConfig.get("page");
        Integer lowerBound = (Integer) pageConfig.get("lowerBound");
        Integer upperBound = (Integer) pageConfig.get("upperBound");

        List<PersonDTO> pageItems = persons.subList(lowerBound, upperBound).stream().map(PersonDTO::new).toList();

        return new PageDTO<>(pageItems, page, size, persons.size());
    }

    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll(defaultSort).stream().map(PersonDTO::new).toList();
    }

    public PersonDTO getPersonById(long id) {
        return new PersonDTO(fetchPerson(id));
    }

    public PersonDTO updatePerson(UpdatePersonDTO updatePersonDTO) {
        Person person = fetchPerson(updatePersonDTO.getId());
        updatePersonDTO.applyChanges(person);
        return new PersonDTO(personRepository.save(person));
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
