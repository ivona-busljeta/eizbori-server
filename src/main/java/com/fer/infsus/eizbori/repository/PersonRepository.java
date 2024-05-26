package com.fer.infsus.eizbori.repository;

import com.fer.infsus.eizbori.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findByFirstNameContainsIgnoreCase(String firstName, Pageable pageable);

    Page<Person> findByLastNameContainsIgnoreCase(String lastName, Pageable pageable);

    Page<Person> findByPidContains(String pid, Pageable pageable);
}
