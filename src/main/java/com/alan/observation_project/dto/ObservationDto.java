package com.alan.observation_project.dto;

import lombok.Data;

@Data
public class ObservationDto {

    private String type; // "MAMMIFERE" ou "POISSON"

    private String animalMarin;

    private String ilot;

    private Double distanceBord;

    private String dateObservation;

    private String qualite;

    private Double tailleEstimee;

    private Integer tempsApneeObserve;

    private Boolean estUnBanc;

    private Integer nombreIndividus;
}
