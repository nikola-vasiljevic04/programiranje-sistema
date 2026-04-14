package com.ecotravel.client;

import com.ecotravel.client.dto.GuideLanguageDTO;
import com.ecotravel.client.dto.GuideProfileResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "vodici-service", url = "http://localhost:8082/api")
public interface GuideClient {

    @GetMapping("/guide-profiles/{id}")
    GuideProfileResponseDTO getGuideById(@PathVariable("id") Long id);

    @GetMapping("/guide-languages/guide/{id}")
    List<GuideLanguageDTO> getGuideLanguages(@PathVariable("id") Long id);

    @GetMapping("/guide-profiles/{id}/completeness")
    Double getGuideCompleteness(@PathVariable("id") Long id);
}