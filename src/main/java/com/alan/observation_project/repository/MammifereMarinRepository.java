package com.alan.observation_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alan.observation_project.entity.MammifereObservation;
import com.alan.observation_project.entity.MammifereObservation.MammifereMarin;
import com.alan.observation_project.entity.Observation;

@Repository
public interface MammifereMarinRepository extends JpaRepository<MammifereObservation, Long> {

    @Query("SELECT o FROM Observation o WHERE o.typeMammifere = :animalMarin")
    List<Observation> findByMammifereMarin(@Param("animalMarin") MammifereMarin typeMammifere);
}
