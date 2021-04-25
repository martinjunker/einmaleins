package de.h2cl.einmaleins.gx;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.Person;

public interface GameExchange extends Serializable {

    void addPerson(Person person);

    List<Person> getPersons();

    void addGame(Game game);

    List<Game> getGames();

    void borrowGameTo(Game game, Person borrower);

    /**
     * Give a borrowed Game back to the owner
     *
     * @param game The game that's given back
     * @return true if was borrowed and is now returned to owner
     */
    boolean giveGameBack(Game game);

    Optional<Person> borrowerOfGame(Game game);
}
