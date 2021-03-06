package de.h2cl.einmaleins.gx;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.GameRepository;
import de.h2cl.einmaleins.domain.Person;
import de.h2cl.einmaleins.domain.PersonRepository;
import de.h2cl.einmaleins.domain.Title;
import de.h2cl.einmaleins.domain.TitleRepository;

@Component
@AllArgsConstructor
public class GameExchangeImpl implements GameExchange {

    private final TitleRepository titles;
    private final GameRepository games;
    private final PersonRepository persons;

    @Override
    public void borrowGameTo(final String gameId, final String borrowerId) {
        games.updateBorrowerId(gameId, borrowerId);
    }

    @Override
    public void giveGameBack(final Game game) {
        game.setBorrowerId(null);
        games.save(game);
    }

    @Override
    public Optional<String> borrowerOfGame(final Game game) {
        return games.findById(game.getId()).map(Game::getBorrowerId);
    }

    @Override
    public List<Game> findGamesToBorrow(String ownerId) {
        return games.findAllByOwnerIdNotLike(ownerId)
                .stream()
                .filter(game -> borrower.findById(game.getId()).isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> findGamesByOwnerOrBorrower(String personId) {
        return games.findAllByOwnerIdOrBorrowerId(personId);
    }

    @Override
    public Optional<Person> findPerson(final String personId) {
        return persons.findById(personId);
    }

    @Override
    public void removeGame(final String gameId) {
        games.deleteById(gameId);
    }

    public Optional<Title> loadTitleOfGame(final String titleId) {
        return titles.findById(titleId);
    }
}
