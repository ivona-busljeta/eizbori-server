package com.fer.infsus.eizbori.repository;

import com.fer.infsus.eizbori.entity.ElectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionTypeRepository extends JpaRepository<ElectionType, Long> {
}
