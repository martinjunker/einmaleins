package de.h2cl.einmaleins.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game implements Serializable {

    @Id
    private String id;

    private String titleId;
    private String ownerId;
    private String borrowerId;

}
