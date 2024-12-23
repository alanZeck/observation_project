package com.alan.observation_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ObservationDto {

    @NotBlank(message = "le type doit être renseigné")
    private String type; // "MAMMIFERE" ou "POISSON"

    private String animalMarin;

    @NotBlank(message = "l'ilot doit être renseignée")
    private String ilot;

    @Positive(message = "la distance du bord doit être supérieur à zéro")
    @NotNull(message = "la distance du bord doit être renseignée")
    private Double distanceBord;

    @NotNull(message = "la date d'observation doit être renseignée")
    private String dateObservation;

    @NotNull(message = "la qualité de l'observation doit être renseignée")
    private String qualite;

    private Double tailleEstimee;

    private Integer tempsApneeObserve;

    private Boolean estUnBanc;

    @Positive(message = "le nombre d'individus doit être supérieur à zéro")
    private Integer nombreIndividus;
}
