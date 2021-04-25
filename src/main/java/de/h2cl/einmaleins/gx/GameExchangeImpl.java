package de.h2cl.einmaleins.gx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.Person;

public class GameExchangeImpl implements GameExchange, Serializable {

    private final List<Person> persons = new ArrayList<>();
    private final List<Game> games = new ArrayList<>();
    private final Map<Game, Person> borrower = new HashMap<>();

    @Override
    public void addPerson(final Person person) {
        persons.add(person);
    }

    @Override
    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public void addGame(final Game game) {
        games.add(game);
    }

    @Override
    public List<Game> getGames() {
        return games;
    }

    @Override
    public void borrowGameTo(final Game game, final Person borrowerWish) {
        borrower.put(game, borrowerWish);
    }

    @Override
    public boolean giveGameBack(final Game game) {
        //TODO
        return false;
    }

    @Override
    public Optional<Person> borrowerOfGame(final Game game) {
        return Optional.ofNullable(borrower.get(game));
    }
}
