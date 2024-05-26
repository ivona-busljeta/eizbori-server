package com.fer.infsus.eizbori.controller;

import com.fer.infsus.eizbori.dto.create.AddPersonDTO;
import com.fer.infsus.eizbori.dto.read.PageDTO;
import com.fer.infsus.eizbori.dto.read.PersonDTO;
import com.fer.infsus.eizbori.dto.update.UpdatePersonDTO;
import com.fer.infsus.eizbori.service.PersonService;
import com.fer.infsus.eizbori.service.enums.PersonFilterField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPersons() {
        List<PersonDTO> mockPersonDTOs = Arrays.asList(mock(PersonDTO.class), mock(PersonDTO.class));
        when(personService.getAllPersons()).thenReturn(mockPersonDTOs);

        List<PersonDTO> result = personController.getPersons();

        assertEquals(mockPersonDTOs, result);
        verify(personService, times(1)).getAllPersons();
    }

    @Test
    void testGetPage() {
        String field = "firstName";
        String term = "Tomislav";
        int page = 0;
        int size = 10;
        long totalSize = 100; // Assuming a total size of 100 for the example

        List<PersonDTO> mockPersonDTOs = Arrays.asList(mock(PersonDTO.class), mock(PersonDTO.class));
        PageDTO<PersonDTO> mockPageDTO = new PageDTO<>(mockPersonDTOs, page, size, totalSize);

        when(personService.getPersonsPage(PersonFilterField.fromLabel(field), term, page, size)).thenReturn(mockPageDTO);

        PageDTO<PersonDTO> result = personController.getPage(field, term, page, size);

        assertEquals(mockPageDTO, result);
        verify(personService, times(1)).getPersonsPage(PersonFilterField.fromLabel(field), term, page, size);
    }

    @Test
    void testGetPageContainingPerson() {
        int id = 1;
        int size = 10;
        long totalSize = 100; // Assuming a total size of 100 for the example

        List<PersonDTO> mockPersonDTOs = Arrays.asList(mock(PersonDTO.class), mock(PersonDTO.class));
        PageDTO<PersonDTO> mockPageDTO = new PageDTO<>(mockPersonDTOs, 0, size, totalSize);
        when(personService.getPageContainingPerson(id, size)).thenReturn(mockPageDTO);

        PageDTO<PersonDTO> result = personController.getPageContainingPerson(id, size);

        assertEquals(mockPageDTO, result);
        verify(personService, times(1)).getPageContainingPerson(id, size);
    }

    @Test
    void testGetPerson() {
        int id = 1;
        PersonDTO mockPersonDTO = mock(PersonDTO.class);
        when(personService.getPersonById(id)).thenReturn(mockPersonDTO);

        PersonDTO result = personController.getPerson(id);

        assertEquals(mockPersonDTO, result);
        verify(personService, times(1)).getPersonById(id);
    }

    @Test
    void testAddPerson() {
        AddPersonDTO mockAddPersonDTO = mock(AddPersonDTO.class);
        PersonDTO mockPersonDTO = mock(PersonDTO.class);
        when(personService.savePerson(mockAddPersonDTO)).thenReturn(mockPersonDTO);

        PersonDTO result = personController.addPerson(mockAddPersonDTO);

        assertEquals(mockPersonDTO, result);
        verify(personService, times(1)).savePerson(mockAddPersonDTO);
    }

    @Test
    void testUpdatePerson() {
        UpdatePersonDTO mockUpdatePersonDTO = mock(UpdatePersonDTO.class);
        PersonDTO mockPersonDTO = mock(PersonDTO.class);
        when(personService.updatePerson(mockUpdatePersonDTO)).thenReturn(mockPersonDTO);

        PersonDTO result = personController.updatePerson(mockUpdatePersonDTO);

        assertEquals(mockPersonDTO, result);
        verify(personService, times(1)).updatePerson(mockUpdatePersonDTO);
    }

    @Test
    void testDeletePerson() {
        int id = 1;

        personController.deletePerson(id);

        verify(personService, times(1)).deletePersonById(id);
    }
}