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
@DiscriminatorValue("POISSON")
public class PoissonObservation extends Observation {

    private Boolean estUnBanc;

    private Integer nombreIndividus;

    @NotNull(message = "le type de poisson doit être renseignée")
    @Enumerated(EnumType.STRING)
    private Poisson typePoisson;

    public enum Poisson {
        RAIE_MANTA,
        THON_A_DENT_DE_CHIEN,
        THAZARD
    }
}
