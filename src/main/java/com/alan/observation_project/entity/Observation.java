package com.alan.observation_project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Observation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "l'ilot doit être renseignée")
    private String ilot;

    @Positive(message = "la distance du bord doit être supérieur à zéro")
    @NotNull(message = "la distance du bord doit être renseignée")
    private Double distanceBord;

    @NotNull(message = "la date d'observation doit être renseignée")
    private LocalDateTime dateObservation;

    private Double tailleEstimee;

    @NotNull(message = "la qualité de l'observation doit être renseignée")
    @Enumerated(EnumType.STRING)
    private QualiteIdentification qualite;

    
    public enum QualiteIdentification {
        SUSPICION, PROBABLE, VERIFIE
    }
}
