package com.alan.observation_project.entity;

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
@DiscriminatorValue("MAMMIFERE")
public class MammifereObservation extends Observation {

    private Double tailleEstimee;

    private Integer tempsApneeObserve;

    @NotNull(message = "le type de mammifere marin doit être renseignée")
    @Enumerated(EnumType.STRING)
    private MammifereMarin typeMammifere;

    public enum MammifereMarin {
        BALEINE_A_BOSSE,
        DAUPHIN_TURSIOPE,
        DUGONG,
        CACHALOT
    }
}
