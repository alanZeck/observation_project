package com.alan.observation_project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Observation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ilot;

    private Double distanceBord;

    private LocalDateTime dateObservation;

    private Double tailleEstimee;

    @Enumerated(EnumType.STRING)
    private QualiteIdentification qualite;

    public enum QualiteIdentification {
        SUSPICION, PROBABLE, VERIFIE
    }
}
