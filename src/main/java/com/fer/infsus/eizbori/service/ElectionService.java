package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.dto.AddElectionDTO;
import com.fer.infsus.eizbori.dto.ElectionDTO;
import com.fer.infsus.eizbori.dto.UpdateElectionDTO;
import com.fer.infsus.eizbori.exception.InvalidObjectException;
import com.fer.infsus.eizbori.exception.ObjectNotFoundException;
import com.fer.infsus.eizbori.entity.Election;
import com.fer.infsus.eizbori.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;
    private final ElectionTypeService electionTypeService;

    @Autowired
    public ElectionService(ElectionRepository electionRepository, ElectionTypeService electionTypeService) {
        this.electionRepository = electionRepository;
        this.electionTypeService = electionTypeService;
    }

    public ElectionDTO saveElection(AddElectionDTO addElectionDTO) {
        try {
            Election election = new Election();
            election.setType(electionTypeService.fetchElectionType(addElectionDTO.getTypeId()));
            election.setYear(addElectionDTO.getYear());
            election.setEventDate(addElectionDTO.getEventDate());
            election.setDeadline(addElectionDTO.getDeadline());
            election.setStatus(addElectionDTO.getStatus());

            return new ElectionDTO(electionRepository.save(election));

        } catch (Exception e) {
            throw new InvalidObjectException("election");
        }
    }

    public List<ElectionDTO> getAllElections() {
        return electionRepository.findAll().stream().map(ElectionDTO::new).toList();
    }

    public ElectionDTO getElectionById(long id) {
        return new ElectionDTO(fetchElection(id));
    }

    public ElectionDTO updateElection(UpdateElectionDTO updateElectionDTO) {
        Election election = fetchElection(updateElectionDTO.getId());
        election.setStatus(updateElectionDTO.getStatus());
        return new ElectionDTO(electionRepository.save(election));
    }

    public void deleteElectionById(long id) {
        electionRepository.deleteById(id);
    }

    protected Election fetchElection(long id) {
        Optional<Election> election = electionRepository.findById(id);
        if (election.isPresent()) {
            return election.get();
        }
        throw new ObjectNotFoundException("election", id);
    }
}
