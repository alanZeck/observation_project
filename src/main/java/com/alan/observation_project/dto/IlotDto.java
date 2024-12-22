package com.alan.observation_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IlotDto {
    private String id;
    private String titre;
    private String localisation;
}
