package com.fer.infsus.eizbori.controller;

import com.fer.infsus.eizbori.controller.response.DeletionResponse;
import com.fer.infsus.eizbori.dto.create.AddPersonDTO;
import com.fer.infsus.eizbori.dto.read.PageDTO;
import com.fer.infsus.eizbori.dto.read.PersonDTO;
import com.fer.infsus.eizbori.dto.update.UpdatePersonDTO;
import com.fer.infsus.eizbori.service.PersonService;
import com.fer.infsus.eizbori.service.enums.PersonFilterField;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public List<PersonDTO> getPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/page")
    public PageDTO<PersonDTO> getPage(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return personService.getPersonsPage(PersonFilterField.fromLabel(field), term, page, size);
    }

    @GetMapping("/page/containing/{id}")
    public PageDTO<PersonDTO> getPageContainingPerson(
            @PathVariable int id,
            @RequestParam(defaultValue = "10") int size
    ) {
        return personService.getPageContainingPerson(id, size);
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @PostMapping()
    public PersonDTO addPerson(@Valid @RequestBody AddPersonDTO addPersonDTO) {
        return personService.savePerson(addPersonDTO);
    }

    @PutMapping()
    public PersonDTO updatePerson(@Valid @RequestBody UpdatePersonDTO updatePersonDTO) {
        return personService.updatePerson(updatePersonDTO);
    }

    @DeleteMapping("/{id}")
    public DeletionResponse deletePerson(@PathVariable int id) {
        personService.deletePersonById(id);
        return new DeletionResponse(id, "Osoba izbrisana");
    }
}
