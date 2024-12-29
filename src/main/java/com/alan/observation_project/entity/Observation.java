package com.alan.observation_project.entity;

import java.time.LocalDateTime;

import com.alan.observation_project.enums.QualiteIdentification;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Observation {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ilot;

    private Double distanceBordEnM;

    private LocalDateTime dateObservation;

    private Double tailleEstimeeEnCm;

    @Enumerated(EnumType.STRING)
    private QualiteIdentification qualite;
}
