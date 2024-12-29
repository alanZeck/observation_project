package com.alan.observation_project.entity;

import com.alan.observation_project.enums.EspeceMammifereMarin;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(MammifereObservation.MAMMIFERE)
public class MammifereObservation extends Observation {

    private static final String MAMMIFERE = "MAMMIFERE";

    private Integer tempsApneeObserve;

    @NotNull(message = "le type de mammifere marin doit être renseigné")
    @Enumerated(EnumType.STRING)
    private EspeceMammifereMarin especeMammifereMarin;
}
