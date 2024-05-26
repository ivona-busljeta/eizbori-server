package com.fer.infsus.eizbori.controller;

import com.fer.infsus.eizbori.controller.response.DeletionResponse;
import com.fer.infsus.eizbori.dto.create.AddCandidateDTO;
import com.fer.infsus.eizbori.dto.read.CandidateDTO;
import com.fer.infsus.eizbori.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/all/{id}")
    public List<CandidateDTO> getAllElectionCandidates(@PathVariable int id) {
        return candidateService.getAllElectionCandidates(id);
    }

    @PostMapping()
    public CandidateDTO createCandidate(@Valid @RequestBody AddCandidateDTO addCandidateDTO) {
        return candidateService.saveCandidate(addCandidateDTO);
    }

    @DeleteMapping("/{id}")
    public DeletionResponse deleteCandidate(@PathVariable int id) {
        candidateService.deleteCandidateById(id);
        return new DeletionResponse(id, "Kandidat izbrisan");
    }
}
