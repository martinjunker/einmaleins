package de.h2cl.einmaleins.views.games;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import lombok.RequiredArgsConstructor;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.Person;
import de.h2cl.einmaleins.gx.GameExchange;

@RouteAlias(value = "")
@Route(value = "games")
@PageTitle("Game Exchange")
@RequiredArgsConstructor
public class GamesView extends HorizontalLayout {

    private final GameExchange gameExchange;

    @PostConstruct
    public void init() {
        addClassName("games-view-class");


        Grid<Game> grid = new Grid<>();
        grid.setItems(gameExchange.getGames());
        grid.addColumn(Game::getName).setHeader("Name");
        grid.addColumn(g -> g.getOwner().getName()).setHeader("gehört");
        grid.addColumn(g ->
                gameExchange.borrowerOfGame(g)
                        .map(Person::getName)
                        .orElse("- nicht ausgeliehen -")
        ).setHeader("ausgeliehen von");

        add(grid);
    }
}
