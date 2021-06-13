package de.h2cl.einmaleins.gx;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.h2cl.einmaleins.domain.TitleRepository;
import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.GameRepository;
import de.h2cl.einmaleins.domain.Person;
import de.h2cl.einmaleins.domain.PersonRepository;

@DataJpaTest
class GameExchangeImplTest {

    GameExchangeImpl gameExchange;

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    void init() {
        gameExchange = new GameExchangeImpl(titleRepository, gameRepository, personRepository);
    }

    @Test
    void SimpleBorrowTest() {

        Person owner = Person.builder().name("THE OWNER").id(UUID.randomUUID().toString()).build();
        Person borrower = Person.builder().name("THE BORROWER").id(UUID.randomUUID().toString()).build();

        Game game = Game.builder().id(UUID.randomUUID().toString())
                .ownerId(owner.getId())
                .build();

        gameExchange.borrowGameTo(game.getId(), borrower.getId());
        assertThat(gameExchange.borrowerOfGame(game)).isPresent();

        gameExchange.giveGameBack(game);
        assertThat(gameExchange.borrowerOfGame(game)).isNotPresent();
    }

}