package com.alan.observation_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alan.observation_project.entity.MammifereObservation;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.enums.EspeceMammifereMarin;

@Repository
public interface MammifereMarinRepository extends JpaRepository<MammifereObservation, Long> {

    List<Observation> findByEspeceMammifereMarin(@Param("animalMarin") EspeceMammifereMarin especeMammifereMarin);
}
