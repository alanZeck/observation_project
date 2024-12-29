package com.alan.observation_project.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alan.observation_project.dto.ObservationDto;
import com.alan.observation_project.entity.MammifereObservation;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.entity.PoissonObservation;
import com.alan.observation_project.enums.EspeceMammifereMarin;
import com.alan.observation_project.enums.EspecePoisson;
import com.alan.observation_project.enums.QualiteIdentification;
import com.alan.observation_project.enums.TypeAnimalMarin;
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

    private final ObservationRepository observationRepository;
    private final MammifereMarinRepository mammifereMarinRepository;
    private final PoissonRepository poissonRepository;
    private final IlotRepository ilotRepository;

    @Cacheable(value = "observations", key = "#animalMarin == null ? 'all' : #animalMarin")
    public List<ObservationDto> getObservations(String animalMarin) {
        final List<Observation> observations;
        if (!StringUtils.isBlank(animalMarin)) {
            boolean isPoisson = Arrays.stream(EspecePoisson.values())
                .anyMatch(poisson -> poisson.name().equalsIgnoreCase(animalMarin));

            boolean isMammifere = Arrays.stream(EspeceMammifereMarin.values())
                .anyMatch(mammifere -> mammifere.name().equalsIgnoreCase(animalMarin));

            if (isPoisson) {
                observations = poissonRepository.findByEspecePoisson(EspecePoisson.valueOf(animalMarin));
            } else if (isMammifere) {
                observations = mammifereMarinRepository.findByEspeceMammifereMarin(EspeceMammifereMarin.valueOf(animalMarin));
            } else {
                throw new InvalidAnimalMarinException("Animal marin invalide : " + animalMarin);
            }
        } else {
            observations = observationRepository.findAll();
        }

        return observations.stream()
                    .map(ObservationDto::toDto)
                    .toList();
    }

    @CacheEvict(value = "observations", allEntries = true)
    public ObservationDto createObservation(ObservationDto observationDto) {
        final Observation observation;

        if (!ilotRepository.existsById(observationDto.getIlot())) {
            throw new InvalidIlotException("L'îlot avec l'ID " + observationDto.getIlot() + " n'existe pas.");
        }
        if (TypeAnimalMarin.MAMMIFERE.equals(observationDto.getType())) {
            final MammifereObservation mammifereObs = new MammifereObservation();
            mammifereObs.setTailleEstimeeEnCm(observationDto.getTailleEstimeeEnCm());
            mammifereObs.setTempsApneeObserve(observationDto.getTempsApneeObserve());
            mammifereObs.setEspeceMammifereMarin(EspeceMammifereMarin.valueOf(observationDto.getAnimalMarin()));
            observation = mammifereObs;
        } else if (TypeAnimalMarin.POISSON.equals(observationDto.getType())) {
            final PoissonObservation poissonObs = new PoissonObservation();
            poissonObs.setEstUnBanc(observationDto.isEstUnBanc());
            if(Boolean.TRUE.equals(observationDto.isEstUnBanc()) && (observationDto.getNombreIndividus() == null || observationDto.getNombreIndividus() < 2)) {
                throw new IllegalArgumentException("le nombre d'individus doit être strictement supérieur à 1 : " + observationDto.getNombreIndividus());
            } else {
                poissonObs.setTailleEstimeeEnCm(observationDto.getTailleEstimeeEnCm());
            }  
            poissonObs.setNombreIndividus(observationDto.getNombreIndividus());
            poissonObs.setEspecePoisson(EspecePoisson.valueOf(observationDto.getAnimalMarin()));
            observation = poissonObs;
        } else {
            throw new IllegalArgumentException("Type d'observation inconnu : " + observationDto.getType());
        }

        observation.setIlot(observationDto.getIlot());
        observation.setDistanceBordEnM(observationDto.getDistanceBordEnM());
        observation.setDateObservation(observationDto.getDateObservation());
        observation.setQualite(QualiteIdentification.valueOf(observationDto.getQualite()));

        // Sauvegarder et retourner le DTO
        Observation savedObservation = observationRepository.save(observation);
        return ObservationDto.toDto(savedObservation);
    }
}
