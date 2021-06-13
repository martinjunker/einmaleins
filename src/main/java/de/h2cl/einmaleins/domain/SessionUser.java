package de.h2cl.einmaleins.domain;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
@VaadinSessionScope
public class SessionUser {

    private final PersonRepository personRepository;

    public Optional<String> name() {
        return Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public Optional<Person> person() {
        return personRepository
                .findByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
