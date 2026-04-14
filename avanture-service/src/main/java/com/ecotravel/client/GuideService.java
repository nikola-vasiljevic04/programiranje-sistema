package com.ecotravel.client;

import com.ecotravel.client.dto.GuideLanguageDTO;
import com.ecotravel.client.dto.GuideProfileResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuideService {

    private final GuideClient guideClient;

    @Retry(name = "vodici-service", fallbackMethod = "fallbackGetGuide")
    @CircuitBreaker(name = "vodici-service", fallbackMethod = "fallbackGetGuide")
    public GuideProfileResponseDTO getGuideInfo(Long guideId) {
        log.info("Poziv ka vodici-service: Dobavljanje profila za ID: {}", guideId);
        return guideClient.getGuideById(guideId);
    }

    @Retry(name = "vodici-service", fallbackMethod = "fallbackGetLanguages")
    @CircuitBreaker(name = "vodici-service", fallbackMethod = "fallbackGetLanguages")
    public List<GuideLanguageDTO> getLanguages(Long guideId) {
        log.info("Poziv ka vodici-service: Dobavljanje jezika za ID: {}", guideId);
        return guideClient.getGuideLanguages(guideId);
    }

    @Retry(name = "vodici-service", fallbackMethod = "fallbackGetCompleteness")
    @CircuitBreaker(name = "vodici-service", fallbackMethod = "fallbackGetCompleteness")
    public Double getCompleteness(Long guideId) {
        log.info("Poziv ka vodici-service: Provera popunjenosti profila za ID: {}", guideId);
        return guideClient.getGuideCompleteness(guideId);
    }


    public GuideProfileResponseDTO fallbackGetGuide(Long guideId, Throwable t) {
        log.error("Fallback aktiviran za getGuideInfo. Razlog: {}", t.getMessage());
        GuideProfileResponseDTO fallback = new GuideProfileResponseDTO();
        fallback.setId(guideId);
        fallback.setFullName("Informacije nedostupne");
        fallback.setBio("Servis za vodiče trenutno nije u funkciji.");
        return fallback;
    }

    public List<GuideLanguageDTO> fallbackGetLanguages(Long guideId, Throwable t) {
        log.error("Fallback aktiviran za getLanguages. Razlog: {}", t.getMessage());
        // Vraćamo praznu listu umesto null ili greške [cite: 22]
        throw new RuntimeException(t);
    }

    public Double fallbackGetCompleteness(Long guideId, Throwable t) {
        log.error("Fallback aktiviran za getCompleteness. Razlog: {}", t.getMessage());
        // Vraćamo 0.0 kao default vrednost [cite: 22]
        return 0.0;
    }
}