package com.fer.infsus.eizbori.repository;

import com.fer.infsus.eizbori.entity.Election;
import com.fer.infsus.eizbori.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    List<Election> findByStatusNotOrderByEventDateDesc(@NonNull Status status);
}
