package com.alan.observation_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.alan.observation_project.dto.ObservationDto;
import com.alan.observation_project.entity.Ilot;
import com.alan.observation_project.entity.MammifereObservation;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.enums.EspeceMammifereMarin;
import com.alan.observation_project.enums.QualiteIdentification;
import com.alan.observation_project.enums.TypeAnimalMarin;
import com.alan.observation_project.exception.InvalidIlotException;
import com.alan.observation_project.repository.IlotRepository;
import com.alan.observation_project.repository.ObservationRepository;

@SpringBootTest
class ObservationServiceTest {

    @Spy
    private IlotRepository ilotRepository;

    @Spy
    private ObservationRepository observationRepository;

    @InjectMocks
    private ObservationService observationService;

    @Test
    void whenValidObservationDto_thenCreateObservation() {
        // Given
        ObservationDto observationDto = new ObservationDto();
        observationDto.setIlot("123456");
        observationDto.setType(TypeAnimalMarin.MAMMIFERE);
        observationDto.setAnimalMarin("BALEINE_A_BOSSE");
        observationDto.setTailleEstimeeEnCm(200.0);
        observationDto.setTempsApneeObserve(15);
        observationDto.setDistanceBordEnM(50.0);
        observationDto.setDateObservation(LocalDateTime.now());
        observationDto.setQualite("VERIFIE");

        Ilot ilot = new Ilot();
        ilot.setId("123456");
        Mockito.when(ilotRepository.existsById(observationDto.getIlot())).thenReturn(true);

        MammifereObservation observation = new MammifereObservation();
        observation.setIlot(observationDto.getIlot());
        observation.setDistanceBordEnM(observationDto.getDistanceBordEnM());
        observation.setDateObservation(observationDto.getDateObservation());
        observation.setQualite(QualiteIdentification.valueOf(observationDto.getQualite()));
        observation.setTailleEstimeeEnCm(observationDto.getTailleEstimeeEnCm());
        observation.setTempsApneeObserve(observationDto.getTempsApneeObserve());
        observation.setEspeceMammifereMarin(EspeceMammifereMarin.valueOf(observationDto.getAnimalMarin()));

        Mockito.when(observationRepository.save(Mockito.any(Observation.class))).thenReturn(observation);

        // When
        ObservationDto createdObservationDto = observationService.createObservation(observationDto);

        // Then
        assertNotNull(createdObservationDto);
        assertEquals(observationDto.getIlot(), createdObservationDto.getIlot());
        assertEquals(observationDto.getType(), createdObservationDto.getType());
        assertEquals(observationDto.getAnimalMarin(), createdObservationDto.getAnimalMarin());
        Mockito.verify(ilotRepository, Mockito.times(1)).existsById(observationDto.getIlot());
        Mockito.verify(observationRepository, Mockito.times(1)).save(Mockito.any(Observation.class));
    }

    @Test
    void whenIlotNotExist_thenThrowInvalidIlotException() {
        // Given
        ObservationDto observationDto = new ObservationDto();
        observationDto.setIlot("invalidIlotId");

        // When and Then
        assertThrows(InvalidIlotException.class, () -> observationService.createObservation(observationDto));
    }
}