package com.alan.observation_project.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alan.observation_project.dto.ObservationDto;
import com.alan.observation_project.enums.EspeceMammifereMarin;
import com.alan.observation_project.enums.QualiteIdentification;

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
        observation.setDistanceBordEnM(10.5);
        observation.setDateObservation(LocalDateTime.now());
        observation.setQualite(QualiteIdentification.VERIFIE);
        observation.setEspeceMammifereMarin(EspeceMammifereMarin.BALEINE_A_BOSSE);

        ObservationDto observationDto = ObservationDto.toDto(observation);

        Set<ConstraintViolation<ObservationDto>> violations = validator.validate(observationDto);

        assertThat(violations).isEmpty();
    }

    @Test
    void givenInvalidObservation_whenValidated_thenViolationsOccur() {
        MammifereObservation observation = new MammifereObservation();
        observation.setIlot(""); // Invalid: @NotBlank
        observation.setDistanceBordEnM(-5.0); // Invalid: @Positive
        observation.setDateObservation(null); // Invalid: @NotNull
        observation.setQualite(QualiteIdentification.VERIFIE);
        observation.setEspeceMammifereMarin(EspeceMammifereMarin.BALEINE_A_BOSSE);
        
        ObservationDto observationDto = ObservationDto.toDto(observation);

        Set<ConstraintViolation<ObservationDto>> violations = validator.validate(observationDto);

        assertThat(violations).hasSize(3);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("ilot") && v.getMessage().contains("l'ilot doit être renseigné"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("distanceBordEnM") && v.getMessage().contains("la distance du bord doit être supérieur à zéro"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("dateObservation") && v.getMessage().contains("la date d'observation doit être renseignée"));
    }
}
