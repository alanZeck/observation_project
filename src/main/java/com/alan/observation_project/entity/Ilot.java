package com.alan.observation_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Ilot {

    @Id
    private String id; // Correspond au champ "id" de l'API

    @NotBlank(message = "le nom de l'ilot doit être renseignée")
    private String titre;

    @NotBlank(message = "la localisation doit être renseignée")
    private String localisation;
}
