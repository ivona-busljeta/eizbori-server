package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.dto.ElectionTypeDTO;
import com.fer.infsus.eizbori.entity.ElectionType;
import com.fer.infsus.eizbori.exception.ObjectNotFoundException;
import com.fer.infsus.eizbori.repository.ElectionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionTypeService {

    private final ElectionTypeRepository electionTypeRepository;

    @Autowired
    public ElectionTypeService(ElectionTypeRepository electionTypeRepository) {
        this.electionTypeRepository = electionTypeRepository;
    }

    public List<ElectionTypeDTO> getAllElectionTypes() {
        return electionTypeRepository.findAll().stream().map(ElectionTypeDTO::new).toList();
    }

    protected ElectionType fetchElectionType(long id) {
        Optional<ElectionType> electionType = electionTypeRepository.findById(id);
        if (electionType.isPresent()) {
            return electionType.get();
        }
        throw new ObjectNotFoundException("election type", id);
    }
}
