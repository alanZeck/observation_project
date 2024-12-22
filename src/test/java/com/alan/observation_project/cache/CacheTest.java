package com.alan.observation_project.cache;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alan.observation_project.entity.MammifereObservation;
import com.alan.observation_project.entity.MammifereObservation.MammifereMarin;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.entity.Observation.QualiteIdentification;
import com.alan.observation_project.repository.ObservationRepository;
import com.alan.observation_project.service.ObservationService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CacheTest {

    @Autowired
    private ObservationService observationService;

    @Mock
    private ObservationRepository observationRepository;

    @Test
    void givenObservations_whenGetObservations_thenCacheIsUsed() {
        MammifereObservation observation = new MammifereObservation();
        observation.setIlot("Ilot A");
        observation.setDistanceBord(10.5);
        observation.setDateObservation(LocalDateTime.now());
        observation.setQualite(QualiteIdentification.VERIFIE);
        observation.setTypeMammifere(MammifereMarin.BALEINE_A_BOSSE);
        List<Observation> observations = List.of(observation);

        Mockito.when(observationRepository.findAll()).thenReturn(observations);

        // Premier appel : la méthode est exécutée
        List<Observation> result = observationService.getObservations(null);
        assertThat(result).hasSize(1);

        // Deuxième appel : récupéré du cache
        List<Observation> cachedResult = observationService.getObservations(null);
        Mockito.verify(observationRepository, Mockito.times(1)).findAll();

        assertThat(result).isEqualTo(cachedResult);
    }
}
