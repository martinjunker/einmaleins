package de.h2cl.einmaleins.views.games;

import java.util.UUID;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import de.h2cl.einmaleins.domain.Game;
import de.h2cl.einmaleins.domain.GameRepository;
import de.h2cl.einmaleins.domain.SessionUser;
import de.h2cl.einmaleins.views.MainView;

@Route(value = "addGame", layout = MainView.class)
@PageTitle("Game Exchange | Add Game")
@RequiredArgsConstructor
public class AddGameView extends VerticalLayout {

    private final GameRepository gameRepository;

    private final SessionUser user;

    private final Button saveGame = new Button("Save");
    private final Button cancelGame = new Button("Cancel");

    private final Binder<Game> gameBinder = new Binder<>(Game.class);


    @PostConstruct
    public void init() {
        addClassName("games-class");
        setSizeFull();

        add(new H3("Neues Spiel eintragen"));

        add(createGameForm());
        var buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        saveGame.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(saveGame);
        buttonLayout.add(cancelGame);
        add(buttonLayout);
    }

    private FormLayout createGameForm() {
        var formLayout = new FormLayout();

        var gameName = new TextField("Name");
        formLayout.add(gameName);
        var gameImageUrl = new TextField("Bild URL");
        formLayout.add(gameImageUrl);

        cancelGame.addClickListener(e -> clearGameForm());
        saveGame.addClickListener(e -> {
            gameBinder.getBean().setId(UUID.randomUUID().toString());
            gameBinder.getBean().setOwnerId(user.person().orElseThrow().getId());
            gameRepository.save(gameBinder.getBean());
            Notification.show("Spiel gespeichert.");
            clearGameForm();
        });

        // TODO bind titleId        gameBinder.bind(gameImageUrl, Game::getImageUrl, Game::setImageUrl);

        clearGameForm();

        return formLayout;
    }

    private void clearGameForm() {
        gameBinder.setBean(Game.builder()
                .id(UUID.randomUUID().toString())
                .ownerId(UUID.randomUUID().toString())
                .build());
    }
}

