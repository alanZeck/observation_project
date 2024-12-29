package com.alan.observation_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ilot {

    @Id
    @EqualsAndHashCode.Include
    private String id; // Correspond au champ "id" de l'API

    @NotBlank(message = "le nom de l'ilot doit être renseigné")
    private String titre;

    @NotBlank(message = "la localisation doit être renseignée")
    private String localisation;
}
