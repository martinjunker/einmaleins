package de.h2cl.einmaleins.views.games;

import static de.h2cl.einmaleins.views.games.GamesUtils.createImage;

import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import lombok.RequiredArgsConstructor;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.GameRepository;
import de.h2cl.einmaleins.domain.Person;
import de.h2cl.einmaleins.domain.PersonRepository;
import de.h2cl.einmaleins.domain.SessionUser;
import de.h2cl.einmaleins.gx.GameExchange;
import de.h2cl.einmaleins.views.MainView;

@RouteAlias(value = "", layout = MainView.class)
@Route(value = "mygames", layout = MainView.class)
@PageTitle("Game Exchange | Meine Spiele")
@RequiredArgsConstructor
public class MyGamesView extends VerticalLayout {

    private final GameExchange gameExchange;

    private final SessionUser user;

    @PostConstruct
    public void init() {
        addClassName("games-view-class");
        setSizeFull();

        add(new H3("Meine Spiele"));
        add(createGrid());
    }

    private Grid<Game> createGrid() {
        Grid<Game> grid = new Grid<>();
        grid.setHeightFull();
        grid.setItems(getRelatedGames());
        grid.addComponentColumn(game -> createImage(game.getImageUrl(), "image of " + game.getName()));
        grid.addColumn(game -> gameExchange.
                //Game::getName).setSortable(true).setHeader("Name");
        grid.addColumn(g ->
                gameExchange.findPerson(g.getOwnerId())
                        .map(Person::getName)
                        .orElse("- Unbekannt -"))
                .setSortable(true)
                .setHeader("gehört");
        grid.addColumn(g ->
                gameExchange.borrowerOfGame(g)
                        .map(gameExchange::findPerson)
                        .map(person -> person.map(Person::getName)
                                .orElse("- unbekannt -"))
                        .orElse("- nicht ausgeliehen -"))
                .setSortable(true)
                .setHeader("ausgeliehen von");

        grid.addComponentColumn(g -> {
            if (g.getOwnerId().equals(getActualPersonId())) {
                return getDeleteButton(grid, g);
            } else {
                return getReturnButton(grid, g);
            }
        });
        return grid;
    }

    private Button getDeleteButton(final Grid<Game> grid, final Game g) {
        return new Button("löschen", buttonClickEvent -> {
            gameExchange.removeGame(g.getId());
            grid.setItems(getRelatedGames());
        });
    }

    private Button getReturnButton(final Grid<Game> grid, final Game g) {
        return new Button("zurück geben", buttonClickEvent -> {
            gameExchange.giveGameBack(g);
            grid.setItems(getRelatedGames());
        });
    }

    private List<Game> getRelatedGames() {
        return gameExchange.findGamesByOwnerOrBorrower(getActualPersonId());
    }

    private String getActualPersonId() {
        return user.person().map(Person::getId).orElse("");
    }
}