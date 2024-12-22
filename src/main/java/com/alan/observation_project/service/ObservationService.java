package com.alan.observation_project.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alan.observation_project.dto.ObservationDto;
import com.alan.observation_project.entity.MammifereObservation;
import com.alan.observation_project.entity.MammifereObservation.MammifereMarin;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.entity.Observation.QualiteIdentification;
import com.alan.observation_project.entity.PoissonObservation;
import com.alan.observation_project.entity.PoissonObservation.Poisson;
import com.alan.observation_project.exception.InvalidAnimalMarinException;
import com.alan.observation_project.repository.MammifereMarinRepository;
import com.alan.observation_project.repository.ObservationRepository;
import com.alan.observation_project.repository.PoissonRepository;

@Service
public class ObservationService {

    private static final String POISSON = "POISSON";
    private static final String MAMMIFERE = "MAMMIFERE";
    private final ObservationRepository observationRepository;
    private final MammifereMarinRepository mammifereMarinRepository;
    private final PoissonRepository poissonRepository;

    public ObservationService(ObservationRepository observationRepository, MammifereMarinRepository mammifereMarinRepository, PoissonRepository poissonRepository) {
        this.observationRepository = observationRepository;
        this.mammifereMarinRepository = mammifereMarinRepository;
        this.poissonRepository = poissonRepository;
    }

    @Cacheable(value = "observations", key = "#animalMarin == null ? 'all' : #animalMarin")
    public List<Observation> getObservations(String animalMarin) {
        if (animalMarin != null) {
            boolean isPoisson = Arrays.stream(Poisson.values())
                .anyMatch(poisson -> poisson.name().equalsIgnoreCase(animalMarin));

            boolean isMammifere = Arrays.stream(MammifereMarin.values())
                .anyMatch(mammifere -> mammifere.name().equalsIgnoreCase(animalMarin));

            if (isPoisson) {
                return poissonRepository.findByPoisson(Poisson.valueOf(animalMarin));
            } else if (isMammifere) {
                return mammifereMarinRepository.findByMammifereMarin(MammifereMarin.valueOf(animalMarin));
            } else {
                throw new InvalidAnimalMarinException("Animal marin invalide : " + animalMarin);
            }
        }

        return observationRepository.findAll();
    }

    @CacheEvict(value = "observations", allEntries = true)
    public Observation createObservation(ObservationDto observationDto) {
        Observation observation;

        if (MAMMIFERE.equals(observationDto.getType())) {
            MammifereObservation mammifereObs = new MammifereObservation();
            mammifereObs.setTailleEstimee(observationDto.getTailleEstimee());
            mammifereObs.setTempsApneeObserve(observationDto.getTempsApneeObserve());
            mammifereObs.setTypeMammifere(MammifereMarin.valueOf(observationDto.getAnimalMarin()));
            observation = mammifereObs;
        } else if (POISSON.equals(observationDto.getType())) {
            PoissonObservation poissonObs = new PoissonObservation();
            poissonObs.setEstUnBanc(observationDto.getEstUnBanc());
            poissonObs.setNombreIndividus(observationDto.getNombreIndividus());
            poissonObs.setTypePoisson(Poisson.valueOf(observationDto.getAnimalMarin()));
            observation = poissonObs;
        } else {
            throw new IllegalArgumentException("Type d'observation inconnu : " + observationDto.getType());
        }

        observation.setIlot(observationDto.getIlot());
        observation.setDistanceBord(observationDto.getDistanceBord());
        observation.setDateObservation(LocalDateTime.parse(observationDto.getDateObservation()));
        observation.setQualite(QualiteIdentification.valueOf(observationDto.getQualite()));

        return observationRepository.save(observation);
    }
}
