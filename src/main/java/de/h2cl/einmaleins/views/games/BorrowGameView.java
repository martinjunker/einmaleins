package de.h2cl.einmaleins.views.games;

import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.GameRepository;
import de.h2cl.einmaleins.domain.Person;
import de.h2cl.einmaleins.domain.PersonRepository;
import de.h2cl.einmaleins.domain.SessionUser;
import de.h2cl.einmaleins.domain.Title;
import de.h2cl.einmaleins.domain.TitleRepository;
import de.h2cl.einmaleins.gx.GameExchange;
import de.h2cl.einmaleins.views.MainView;

@Route(value = "borrowame", layout = MainView.class)
@PageTitle("Game Exchange | Borrow Game")
@RequiredArgsConstructor
public class BorrowGameView extends VerticalLayout {

    private final GameExchange gameExchange;
    private final GameRepository gameRepository;
    private final TitleRepository titleRepository;
    private final PersonRepository personRepository;

    private final SessionUser user;

    @PostConstruct
    public void init() {
        addClassName("games-class");
        setSizeFull();

        add(new H3("Spiel ausleihen"));
        Grid<Game> grid = new Grid<>();
        grid.setHeightFull();
        grid.setItems(getGamesToBorrow());
        grid.addComponentColumn(game -> createImage(game.getTitleId()));
        grid.addColumn(g -> titleRepository.findById(g.getTitleId()).map(Title::getName)).setSortable(true).setHeader("Name");
        grid.addColumn(g ->
                personRepository.findById(g.getOwnerId())
                        .map(Person::getName)
                        .orElse("- Unbekannt -"))
                .setSortable(true)
                .setHeader("gehört");
        grid.addColumn(g ->
                gameExchange.borrowerOfGame(g)
                        .map(personRepository::findById)
                        .map(person -> person.map(Person::getName)
                                .orElse("- unbekannt -"))
                        .orElse("- nicht ausgeliehen -"))
                .setSortable(true)
                .setHeader("ausgeliehen von");
        grid.addComponentColumn(g ->
                new Button("ausleihen", buttonClickEvent -> {
                    gameExchange.borrowGameTo(g.getId(), user.person().map(Person::getId).orElseThrow());
                    grid.setItems(getGamesToBorrow());
                }));

        add(grid);
    }

    private List<Game> getGamesToBorrow() {
        return gameExchange.findGamesToBorrow(user.person().map(Person::getId).orElse(""));
    }
}
