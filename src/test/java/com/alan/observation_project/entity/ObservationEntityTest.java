package com.alan.observation_project.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alan.observation_project.entity.MammifereObservation.MammifereMarin;
import com.alan.observation_project.entity.Observation.QualiteIdentification;

import jakarta.validation.*;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ObservationEntityTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void givenValidObservation_whenValidated_thenNoViolations() {
        MammifereObservation observation = new MammifereObservation();
        observation.setIlot("Ilot A");
        observation.setDistanceBord(10.5);
        observation.setDateObservation(LocalDateTime.now());
        observation.setQualite(QualiteIdentification.VERIFIE);
        observation.setTypeMammifere(MammifereMarin.BALEINE_A_BOSSE);

        Set<ConstraintViolation<Observation>> violations = validator.validate(observation);

        assertThat(violations).isEmpty();
    }

    @Test
    void givenInvalidObservation_whenValidated_thenViolationsOccur() {
        MammifereObservation observation = new MammifereObservation();
        observation.setIlot(""); // Invalid: @NotBlank
        observation.setDistanceBord(-5.0); // Invalid: @Positive
        observation.setDateObservation(null); // Invalid: @NotNull
        observation.setQualite(null); // Invalid: @NotBlank
        observation.setTypeMammifere(null);// invalid: @NotNull

        Set<ConstraintViolation<Observation>> violations = validator.validate(observation);

        assertThat(violations).hasSize(5);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("ilot") && v.getMessage().contains("l'ilot doit être renseignée"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("distanceBord") && v.getMessage().contains("la distance du bord doit être supérieur à zéro"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("dateObservation") && v.getMessage().contains("la date d'observation doit être renseignée"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("qualite") && v.getMessage().contains("la qualité de l'observation doit être renseignée"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("typeMammifere") && v.getMessage().contains("le type de mammifere marin doit être renseignée"));
    }
}
