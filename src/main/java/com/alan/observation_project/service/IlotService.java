package com.alan.observation_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alan.observation_project.dto.IlotDto;
import com.alan.observation_project.dto.IlotResponseDto;
import com.alan.observation_project.entity.Ilot;
import com.alan.observation_project.repository.IlotRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class IlotService {

    private static final String API_ILOTS_URL = "https://www.province-sud.nc/pandoreweb/pandore/ilot/IlotDto/?start=0&_responseMode=json";
    private final IlotRepository ilotRepository;
    private final RestTemplate restTemplate;

    @PostConstruct
    public void synchronizeIlots() {
        ResponseEntity<IlotResponseDto> response = restTemplate.getForEntity(API_ILOTS_URL, IlotResponseDto.class);
        IlotResponseDto ilotResponse = response.getBody();

       if (ilotResponse != null) {
            log.info("récupération des ilots...");
            Set<String> existingIds = new HashSet<>(ilotRepository.findAllIds());

            List<Ilot> ilots = ilotResponse.getData().stream()
                .map(IlotDto::mapToEntity)
                .filter(ilot -> !existingIds.contains(ilot.getId()))
                .toList();

            ilotRepository.saveAll(ilots);

            log.info("sauvegarde des ilots terminée");
        }
    }
}
