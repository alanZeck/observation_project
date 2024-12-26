package com.alan.observation_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alan.observation_project.dto.IlotDto;
import com.alan.observation_project.dto.IlotResponse;
import com.alan.observation_project.entity.Ilot;
import com.alan.observation_project.repository.IlotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IlotService {

    private static final Logger logger = LoggerFactory.getLogger(IlotService.class);
    private static final String API_ILOTS_URL = "https://www.province-sud.nc/pandoreweb/pandore/ilot/IlotDto/?start=0&_responseMode=json";
    private final IlotRepository ilotRepository;
    private final RestTemplate restTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void synchronizeIlots() {
        ResponseEntity<IlotResponse> response = restTemplate.getForEntity(API_ILOTS_URL, IlotResponse.class);
        IlotResponse ilotResponse = response.getBody();

       if (ilotResponse != null) {
            logger.info("récupération des ilots...");
            Set<String> existingIds = new HashSet<>(ilotRepository.findAllIds());

            List<Ilot> ilots = ilotResponse.getData().stream()
                .map(this::mapToEntity)
                .filter(ilot -> !existingIds.contains(ilot.getId()))
                .toList();

            ilotRepository.saveAll(ilots);

            logger.info("sauvegarde des ilots terminées");
        }
    }

    private Ilot mapToEntity(IlotDto ilotDto) {
        Ilot ilot = new Ilot();
        ilot.setId(ilotDto.getId());
        ilot.setTitre(ilotDto.getTitre());
        ilot.setLocalisation(ilotDto.getLocalisation());
        return ilot;
    }
}
