package com.alan.observation_project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alan.observation_project.dto.ObservationDto;
import com.alan.observation_project.entity.Observation;
import com.alan.observation_project.exception.InvalidAnimalMarinException;
import com.alan.observation_project.service.ObservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    private final ObservationService service;

    public ObservationController(ObservationService service) {
        this.service = service;
    }

    /**
     * Crée une nouvelle observation d'animal marin.
     *
     * @param dto l'objet DTO contenant les informations nécessaires à la création de l'observation.
     *            Doit être valide selon les contraintes définies dans {@code ObservationDto}.
     * @return une réponse contenant l'observation créée si elle a été enregistrée avec succès.
     * @throws MethodArgumentNotValidException si les données fournies dans le DTO sont invalides.
     */
    @Operation(
        summary = "Créer une nouvelle observation",
        description = "Permet de créer une nouvelle observation d'animal marin à partir des données fournies dans le corps de la requête."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Observation créée avec succès", content = @Content(schema = @Schema(implementation = Observation.class))),
        @ApiResponse(responseCode = "400", description = "Requête invalide (données manquantes ou incorrectes)", content = @Content),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Observation> createObservation(@Valid @RequestBody ObservationDto dto) {
        return ResponseEntity.ok(service.createObservation(dto));
    }

    /**
     * Récupère la liste des observations d'animaux marins.
     *
     * @param animalMarin (optionnel) le nom de l'animal marin pour filtrer les observations.
     *                    Ce paramètre peut être un nom valide dans les énumérations {@code MammifereMarin} ou {@code Poisson}.
     *                    Si non fourni, toutes les observations seront retournées.
     * @return une réponse contenant la liste des observations filtrée ou complète.
     * @throws InvalidAnimalMarinException si le paramètre {@code animalMarin} n'est pas valide.
     */
    @Operation(
        summary = "Récupérer les observations",
        description = "Récupère la liste des observations d'animaux marins. Permet de filtrer par type d'animal marin (mammifère ou poisson) en utilisant un paramètre facultatif."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des observations récupérée avec succès", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Observation.class)))),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ObservationDto>> getObservations(@RequestParam(required = false) String animalMarin) {
        return ResponseEntity.ok(service.getObservations(animalMarin));
    }
}
