package de.h2cl.einmaleins.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.Person;
import de.h2cl.einmaleins.gx.GameExchange;
import de.h2cl.einmaleins.gx.GameExchangeImpl;

@Slf4j
@Configuration
public class GameExchangeConfig {

    @Bean
    public GameExchange gameExchange() {

        log.info("loading GameExchange ...");
        GameExchange gx = new GameExchangeImpl();

        Person klaus = Person.builder()
                .uuid(UUID.randomUUID())
                .name("Konsolen Klaus")
                .build();

        gx.addPerson(klaus);

        Game chess = Game.builder()
                .uuid(UUID.randomUUID())
                .name("Chess")
                .imageUrl("https://example.com/chess.png")
                .owner(klaus)
                .build();

        gx.addGame(chess);

        gx.borrowGameTo(chess, klaus);
        return gx;
    }
}
