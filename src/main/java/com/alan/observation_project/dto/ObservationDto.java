package com.alan.observation_project.dto;

import java.time.LocalDateTime;

import com.alan.observation_project.entity.MammifereObservation;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.entity.PoissonObservation;
import com.alan.observation_project.enums.TypeAnimalMarin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ObservationDto {

    private TypeAnimalMarin type; // "MAMMIFERE" ou "POISSON"

    private String animalMarin;

    @NotBlank(message = "l'ilot doit être renseigné")
    private String ilot;

    @Positive(message = "la distance du bord doit être supérieur à zéro")
    @NotNull(message = "la distance du bord doit être renseignée")
    private Double distanceBordEnM;

    @NotNull(message = "la date d'observation doit être renseignée")
    private LocalDateTime dateObservation;

    @NotNull(message = "la qualité de l'observation doit être renseignée")
    private String qualite;

    private Double tailleEstimeeEnCm;

    private Integer tempsApneeObserve;

    private boolean estUnBanc;

    @Positive(message = "le nombre d'individus doit être supérieur à zéro")
    private Integer nombreIndividus;

    public static ObservationDto toDto(Observation observation) {
        ObservationDto dto = new ObservationDto();
        dto.setIlot(observation.getIlot());
        dto.setDistanceBordEnM(observation.getDistanceBordEnM());
        dto.setDateObservation(observation.getDateObservation());
        dto.setQualite(observation.getQualite().name());
        dto.setTailleEstimeeEnCm(observation.getTailleEstimeeEnCm());

        if (observation instanceof MammifereObservation mammifereObservation) {
            dto.setType(TypeAnimalMarin.MAMMIFERE);
            dto.setAnimalMarin(mammifereObservation.getEspeceMammifereMarin().name());
            dto.setTempsApneeObserve(mammifereObservation.getTempsApneeObserve());
        } else if (observation instanceof PoissonObservation poissonObservation) {
            dto.setType(TypeAnimalMarin.POISSON);
            dto.setAnimalMarin(poissonObservation.getEspecePoisson().name());
            dto.setEstUnBanc(poissonObservation.isEstUnBanc());
            dto.setNombreIndividus(poissonObservation.getNombreIndividus());
        }

        return dto;
    }
}
