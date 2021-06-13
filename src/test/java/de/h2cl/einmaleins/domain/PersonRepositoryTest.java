package de.h2cl.einmaleins.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    void simpleInsertFindDelete() {
        Person game = Person.builder()
                .id(UUID.randomUUID().toString())
                .name("NAME")
                .build();

        personRepository.save(game);
        assertThat(personRepository.findById(game.getId())).isPresent();

        personRepository.deleteAll();
        assertThat(personRepository.findById(game.getId())).isNotPresent();
    }
}
