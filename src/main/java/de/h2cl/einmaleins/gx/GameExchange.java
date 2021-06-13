package de.h2cl.einmaleins.gx;

import java.util.List;
import java.util.Optional;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.Person;

public interface GameExchange {

    void borrowGameTo(String gameId, String borrowerId);

    /**
     * Give a borrowed Game back to the owner
     *
     * @param game The game that's given back
     */
    void giveGameBack(Game game);

    Optional<String> borrowerOfGame(Game game);

    List<Game> findGamesToBorrow(String ownerId);

    List<Game> findGamesByOwnerOrBorrower(String personId);

    Optional<Person> findPerson(String id);

    void removeGame(String id);
}
