package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.dto.read.PartyDTO;
import com.fer.infsus.eizbori.entity.Party;
import com.fer.infsus.eizbori.exception.ObjectNotFoundException;
import com.fer.infsus.eizbori.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    @Autowired
    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    public List<PartyDTO> getAllParties() {
        return partyRepository.findAll().stream().map(PartyDTO::new).toList();
    }

    protected Party fetchParty(long id) {
        Optional<Party> party = partyRepository.findById(id);
        if (party.isPresent()) {
            return party.get();
        }
        throw new ObjectNotFoundException("party", id);
    }
}
