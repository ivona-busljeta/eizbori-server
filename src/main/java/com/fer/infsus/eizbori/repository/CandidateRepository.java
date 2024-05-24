package com.fer.infsus.eizbori.repository;

import com.fer.infsus.eizbori.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Collection<Candidate> findAllByElectionId(Long electionId);
}
