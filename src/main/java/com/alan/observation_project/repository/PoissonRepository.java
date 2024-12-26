package com.alan.observation_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.entity.PoissonObservation;
import com.alan.observation_project.entity.PoissonObservation.Poisson;

@Repository
public interface PoissonRepository extends JpaRepository<PoissonObservation, Long> {

    @Query("SELECT o FROM Observation o WHERE o.typePoisson = :animalMarin")
    List<Observation> findByPoisson(@Param("animalMarin") Poisson typePoisson);
}
