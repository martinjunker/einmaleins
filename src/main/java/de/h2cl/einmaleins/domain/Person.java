package de.h2cl.einmaleins.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person implements Serializable {

    private final UUID uuid;
    private final String name;

}