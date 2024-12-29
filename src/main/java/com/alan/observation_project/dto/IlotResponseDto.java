package com.alan.observation_project.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IlotResponseDto {
    private List<IlotDto> data;

    public List<IlotDto> getData() {
        return data;
    }

    public void setData(List<IlotDto> data) {
        this.data = data;
    }
}
