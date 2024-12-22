package com.alan.observation_project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alan.observation_project.dto.ObservationDto;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.service.ObservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    private final ObservationService service;

    public ObservationController(ObservationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Observation> createObservation(@Valid @RequestBody ObservationDto dto) {
        return ResponseEntity.ok(service.createObservation(dto));
    }

    @GetMapping
    public List<Observation> getObservations(@RequestParam(required = false) String animalMarin) {
        return service.getObservations(animalMarin);
    }
}
