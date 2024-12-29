package com.alan.observation_project.entity;

import com.alan.observation_project.enums.EspecePoisson;

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
@DiscriminatorValue(PoissonObservation.POISSON)
public class PoissonObservation extends Observation {

    private static final String POISSON = "POISSON";

    private boolean estUnBanc;

    private Integer nombreIndividus;

    @NotNull(message = "le type de poisson doit être renseigné")
    @Enumerated(EnumType.STRING)
    private EspecePoisson especePoisson;
}
