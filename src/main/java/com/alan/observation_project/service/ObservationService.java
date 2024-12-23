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
import com.alan.observation_project.exception.InvalidIlotException;
import com.alan.observation_project.repository.IlotRepository;
import com.alan.observation_project.repository.MammifereMarinRepository;
import com.alan.observation_project.repository.ObservationRepository;
import com.alan.observation_project.repository.PoissonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObservationService {

    private static final String POISSON = "POISSON";
    private static final String MAMMIFERE = "MAMMIFERE";
    private final ObservationRepository observationRepository;
    private final MammifereMarinRepository mammifereMarinRepository;
    private final PoissonRepository poissonRepository;
    private final IlotRepository ilotRepository;

    @Cacheable(value = "observations", key = "#animalMarin == null ? 'all' : #animalMarin")
    public List<ObservationDto> getObservations(String animalMarin) {
        final List<Observation> observations;
        if (animalMarin != null) {
            boolean isPoisson = Arrays.stream(Poisson.values())
                .anyMatch(poisson -> poisson.name().equalsIgnoreCase(animalMarin));

            boolean isMammifere = Arrays.stream(MammifereMarin.values())
                .anyMatch(mammifere -> mammifere.name().equalsIgnoreCase(animalMarin));

            if (isPoisson) {
                observations = poissonRepository.findByPoisson(Poisson.valueOf(animalMarin));
            } else if (isMammifere) {
                observations = mammifereMarinRepository.findByMammifereMarin(MammifereMarin.valueOf(animalMarin));
            } else {
                throw new InvalidAnimalMarinException("Animal marin invalide : " + animalMarin);
            }
        } else {
            observations = observationRepository.findAll();
        }

        return observations.stream()
                    .map(this::toDto)
                    .toList();
    }

    @CacheEvict(value = "observations", allEntries = true)
    public Observation createObservation(ObservationDto observationDto) {
        final Observation observation;

        if (!ilotRepository.existsById(observationDto.getIlot())) {
            throw new InvalidIlotException("L'îlot avec l'ID " + observationDto.getIlot() + " n'existe pas.");
        }
        if (MAMMIFERE.equals(observationDto.getType())) {
            final MammifereObservation mammifereObs = new MammifereObservation();
            mammifereObs.setTailleEstimee(observationDto.getTailleEstimee());
            mammifereObs.setTempsApneeObserve(observationDto.getTempsApneeObserve());
            mammifereObs.setTypeMammifere(MammifereMarin.valueOf(observationDto.getAnimalMarin()));
            observation = mammifereObs;
        } else if (POISSON.equals(observationDto.getType())) {
            final PoissonObservation poissonObs = new PoissonObservation();
            poissonObs.setEstUnBanc(observationDto.getEstUnBanc());
            if(Boolean.TRUE.equals(observationDto.getEstUnBanc()) && (observationDto.getNombreIndividus() == null || observationDto.getNombreIndividus() < 2)) {
                throw new IllegalArgumentException("le nombre d'individus doit être strictement supérieur à 1 : " + observationDto.getNombreIndividus());
            } else {
                poissonObs.setTailleEstimee(observationDto.getTailleEstimee());
            }  
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

    private ObservationDto toDto(Observation observation) {
        ObservationDto dto = new ObservationDto();
        dto.setIlot(observation.getIlot());
        dto.setDistanceBord(observation.getDistanceBord());
        dto.setDateObservation(observation.getDateObservation().toString());
        dto.setQualite(observation.getQualite().name());
        dto.setTailleEstimee(observation.getTailleEstimee());

        if (observation instanceof MammifereObservation mammifereObservation) {
            dto.setType(MAMMIFERE);
            dto.setAnimalMarin(mammifereObservation.getTypeMammifere().name());
            dto.setTempsApneeObserve(mammifereObservation.getTempsApneeObserve());
        } else if (observation instanceof PoissonObservation poissonObservation) {
            dto.setType(POISSON);
            dto.setAnimalMarin(poissonObservation.getTypePoisson().name());
            dto.setEstUnBanc(poissonObservation.getEstUnBanc());
            dto.setNombreIndividus(poissonObservation.getNombreIndividus());
        }

        return dto;
    }
}
