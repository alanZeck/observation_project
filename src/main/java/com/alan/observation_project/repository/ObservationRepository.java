package com.alan.observation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alan.observation_project.entity.Observation;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
}
