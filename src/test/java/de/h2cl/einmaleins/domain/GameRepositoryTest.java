package de.h2cl.einmaleins.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @BeforeEach
    void setup() {
        gameRepository.deleteAll();
    }

    @Test
    void findAllByOwnerIdOrBorrowerIdTest() {
        Game gameByOwner = Game.builder()
                .id("GAME BY OWNER")
                .ownerId("PERSON_1")
                .build();
        gameRepository.save(gameByOwner);

        Game gameByBorrower = Game.builder()
                .id("GAME BY BORROWER")
                .ownerId("PERSON_2")
                .borrowerId("PERSON_1")
                .build();
        gameRepository.save(gameByBorrower);

        Game anotherGame = Game.builder()
                .id("Another Game")
                .ownerId("PERSON_3")
                .build();
        gameRepository.save(anotherGame);

        assertThat(gameRepository.findAllByOwnerIdOrBorrowerId("PERSON_1"))
                .containsExactly(gameByOwner, gameByBorrower);
    }

}