package com.fer.infsus.eizbori.controller;

import com.fer.infsus.eizbori.controller.response.DeletionResponse;
import com.fer.infsus.eizbori.dto.create.AddElectionDTO;
import com.fer.infsus.eizbori.dto.read.ElectionDTO;
import com.fer.infsus.eizbori.dto.update.UpdateElectionDTO;
import com.fer.infsus.eizbori.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/election")
public class ElectionController {

    private final ElectionService electionService;

    @Autowired
    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping("/all")
    public List<ElectionDTO> getElections() {
        return electionService.getAllElections();
    }

    @GetMapping("/{id}")
    public ElectionDTO getElection(@PathVariable int id) {
        return electionService.getElectionById(id);
    }

    @PostMapping()
    public ElectionDTO createElection(@RequestBody AddElectionDTO addElectionDTO) {
        return electionService.saveElection(addElectionDTO);
    }

    @PutMapping()
    public ElectionDTO updateElection(@RequestBody UpdateElectionDTO updateElectionDTO) {
        return electionService.updateElection(updateElectionDTO);
    }

    @DeleteMapping("/{id}")
    public DeletionResponse deleteElection(@PathVariable int id) {
        electionService.deleteElectionById(id);
        return new DeletionResponse(id, "Izbori izbrisani");
    }
}
