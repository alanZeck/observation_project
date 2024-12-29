package com.alan.observation_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.entity.PoissonObservation;
import com.alan.observation_project.enums.EspecePoisson;

@Repository
public interface PoissonRepository extends JpaRepository<PoissonObservation, Long> {

    List<Observation> findByEspecePoisson(@Param("animalMarin") EspecePoisson especePoisson);
}
