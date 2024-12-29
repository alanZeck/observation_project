package com.alan.observation_project.dto;

import com.alan.observation_project.entity.Ilot;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IlotDto {
    private String id;
    private String titre;
    private String localisation;

    public static Ilot mapToEntity(IlotDto ilotDto) {
        Ilot ilot = new Ilot();
        ilot.setId(ilotDto.getId());
        ilot.setTitre(ilotDto.getTitre());
        ilot.setLocalisation(ilotDto.getLocalisation());
        return ilot;
    }
}
