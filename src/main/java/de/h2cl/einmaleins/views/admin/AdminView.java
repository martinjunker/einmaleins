package de.h2cl.einmaleins.views.admin;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import de.h2cl.einmaleins.domain.GameRepository;
import de.h2cl.einmaleins.domain.Person;
import de.h2cl.einmaleins.domain.PersonRepository;
import de.h2cl.einmaleins.views.MainView;

@Route(value = "admin", layout = MainView.class)
@PageTitle("Game Exchange | Admin")
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
@CssImport("./views/admin/admin-view.css")
public class AdminView extends HorizontalLayout {

    private final PersonRepository personRepository;
    private final GameRepository gameRepository;
    private final JdbcUserDetailsManager userDetailsService;
    private final PasswordEncoder passwordEncoder;


    private final Grid<Person> grid = new Grid<>();

    private final Binder<Person> personBinder = new Binder<>(Person.class);
    private final TextField name = new TextField("Name");

    @PostConstruct
    public void init() {
        addClassName("admin-view");
        add(new H3("Administration"));

        createPersonForm();
        add(initPersonGrid());
    }

    private void createPersonForm() {
        add(new H3("Neue Person hinzufügen"));
        var formLayout = new FormLayout();
        formLayout.add(name);
        add(formLayout);

        var savePerson = new Button("Save");
        var cancelPerson = new Button("Cancel");
        var buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        savePerson.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(savePerson);
        buttonLayout.add(cancelPerson);

        add(buttonLayout);

        personBinder.bind(name, Person::getName, Person::setName);
        clearPersonForm();

        cancelPerson.addClickListener(e -> clearPersonForm());
        savePerson.addClickListener(e -> {
            personBinder.getBean().setId(UUID.randomUUID().toString());
            personRepository.save(personBinder.getBean());
            Notification.show(personBinder.getBean().getName() + " gespeichert.");
            clearPersonForm();
            grid.setItems(personRepository.findAll());
        });
    }

    private Grid<Person> initPersonGrid() {
        grid.setItems(personRepository.findAll());
        grid.addColumn(Person::getName).setHeader("Name");
        grid.addColumn(p -> gameRepository.countGamesByOwnerId(p.getId())).setHeader("Anzahl Spiele");
        grid.addComponentColumn(p ->
                new Button("generate new access",
                        buttonClickEvent -> {
                            userDetailsService.deleteUser(p.getName());
                            var password = "blabla";
                            UserDetails user = new User(p.getName(), passwordEncoder.encode(password), List.of(new SimpleGrantedAuthority("ROLE_USER")));
                            userDetailsService.createUser(user);
                            Notification.show("User " + p.getName() + " angelegt. Password: " + password);
                        }
                )
        );
        grid.addComponentColumn(p ->
                new Button(new Icon(VaadinIcon.MINUS), buttonClickEvent -> {
                    personRepository.deleteById(p.getId());
                    grid.setItems(personRepository.findAll());
                }));
        return grid;
    }

    private void clearPersonForm() {
        personBinder.setBean(Person.builder().id(UUID.randomUUID().toString()).build());
    }
}
