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
