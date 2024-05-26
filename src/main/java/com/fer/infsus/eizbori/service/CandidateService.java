package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.dto.create.AddCandidateDTO;
import com.fer.infsus.eizbori.dto.read.CandidateDTO;
import com.fer.infsus.eizbori.entity.Election;
import com.fer.infsus.eizbori.exception.InvalidObjectException;
import com.fer.infsus.eizbori.entity.Candidate;
import com.fer.infsus.eizbori.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final ElectionService electionService;
    private final PersonService personService;
    private final PositionService positionService;
    private final PartyService partyService;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository, ElectionService electionService, PersonService personService, PositionService positionService, PartyService partyService) {
        this.candidateRepository = candidateRepository;
        this.electionService = electionService;
        this.personService = personService;
        this.positionService = positionService;
        this.partyService = partyService;
    }

    public CandidateDTO saveCandidate(AddCandidateDTO addCandidateDTO) {
        try {
            Candidate candidate = new Candidate();
            candidate.setElection(electionService.fetchElection(addCandidateDTO.getElectionId()));
            candidate.setPerson(personService.fetchPerson(addCandidateDTO.getPersonId()));
            candidate.setPosition(positionService.fetchPosition(addCandidateDTO.getPositionId()));
            if (addCandidateDTO.getPartyId() != null) {
                candidate.setParty(partyService.fetchParty(addCandidateDTO.getPartyId()));
            }
            candidate.setApplied(addCandidateDTO.getApplicationDate());

            return new CandidateDTO(candidateRepository.save(candidate));

        } catch (Exception e) {
            throw new InvalidObjectException("candidate");
        }
    }

    public List<CandidateDTO> getAllElectionCandidates(long electionId) {
        Election election = electionService.fetchElection(electionId);
        return candidateRepository.findAllByElection(election).stream().map(CandidateDTO::new).toList();
    }

    public void deleteCandidateById(long id) {
        candidateRepository.deleteById(id);
    }
}
