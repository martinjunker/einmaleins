package de.h2cl.einmaleins.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import de.h2cl.einmaleins.gx.GameExchange;
import de.h2cl.einmaleins.gx.GameExchangeImpl;

class GameExchangeImplTest {

    GameExchange gameExchange = new GameExchangeImpl();

    @Test
    void addPerson() {
        Person person = Person.builder().name("Name").build();
        gameExchange.addPerson(person);

        assertThat(gameExchange.getPersons()).contains(person);
    }

    @Test
    void addGame() {
        Game game = Game.builder().uuid(UUID.randomUUID()).name("Chess").build();
        gameExchange.addGame(game);

        assertThat(gameExchange.getGames()).contains(game);
    }

}