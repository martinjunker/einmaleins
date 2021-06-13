package de.h2cl.einmaleins.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {

    Optional<Person> findByName(String name);

}
