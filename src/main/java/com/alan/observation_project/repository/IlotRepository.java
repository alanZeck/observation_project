package com.alan.observation_project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alan.observation_project.entity.Ilot;

@Repository
public interface IlotRepository  extends JpaRepository<Ilot, String>{

    @Query("SELECT i.id FROM Ilot i")
    Set<String> findAllIds();
}
