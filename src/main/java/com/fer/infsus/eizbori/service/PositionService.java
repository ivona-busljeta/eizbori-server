package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.dto.read.PositionDTO;
import com.fer.infsus.eizbori.entity.Position;
import com.fer.infsus.eizbori.exception.ObjectNotFoundException;
import com.fer.infsus.eizbori.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<PositionDTO> getAllPositions() {
        return this.positionRepository.findAll().stream().map(PositionDTO::new).toList();
    }

    protected Position fetchPosition(long id) {
        Optional<Position> position = this.positionRepository.findById(id);
        if (position.isPresent()) {
            return position.get();
        }
        throw new ObjectNotFoundException("position", id);
    }
}
