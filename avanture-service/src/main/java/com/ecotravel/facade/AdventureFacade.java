package com.ecotravel.facade;

import com.ecotravel.client.GuideService;
import com.ecotravel.client.dto.GuideLanguageDTO;
import com.ecotravel.client.dto.GuideProfileResponseDTO;
import com.ecotravel.dtos.AdventureResponseDTO;
import com.ecotravel.dtos.GuideDTO;
import com.ecotravel.factory.AdventureReport;
import com.ecotravel.factory.AdventureReportFactory;
import com.ecotravel.model.Adventure;
import com.ecotravel.service.AdventureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdventureFacade {

    private final AdventureService adventureService;
    private final GuideService guideService;
    private final AdventureReportFactory reportFactory;

    public AdventureResponseDTO getAdventureFullDetails(Long id) {
        Adventure adventure = adventureService.getRawById(id);
        AdventureResponseDTO dto = adventureService.getById(id);

        if (adventure.getGuideId() != null) {
            try {
                log.info("Facade poziva eksterni vodic-service za ID: {}", adventure.getGuideId());
                GuideProfileResponseDTO profile = guideService.getGuideInfo(adventure.getGuideId());

                GuideDTO guideDto = new GuideDTO();
                guideDto.setId(profile.getId());
                guideDto.setFullName(profile.getFullName());
                guideDto.setContactEmail(profile.getContactEmail());
                guideDto.setRating(profile.getRating());

                dto.setGuide(guideDto);
            } catch (Exception e) {
                log.error("Facade: Greška pri komunikaciji sa vodici-service: {}", e.getMessage());
            }
        }
        return dto;
    }

    public List<AdventureResponseDTO> getAllAdventuresWithGuides() {
        return adventureService.getAllRaw().stream()
                .map(adv -> getAdventureFullDetails(adv.getId()))
                .collect(Collectors.toList());
    }

    public String verifyGuide(Long adventureId, String requiredLanguage) {
        Adventure adventure = adventureService.getRawById(adventureId);

        if (adventure.getGuideId() == null) {
            return "Ova avantura nema dodeljenog vodiča.";
        }

        Long guideId = adventure.getGuideId();

        GuideProfileResponseDTO guide = guideService.getGuideInfo(guideId);

        if (guide.getFullName() != null && guide.getFullName().contains("nedostupno")) {
            return "Validacija trenutno nije moguća: Servis za vodiče nije dostupan.";
        }

        List<GuideLanguageDTO> languages = guideService.getLanguages(guideId);
        boolean knowsLanguage = languages != null && languages.stream()
                .anyMatch(l -> l.getLanguageName().equalsIgnoreCase(requiredLanguage));

        if (!knowsLanguage) {
            return "Vodič " + guide.getFullName() + " ne ispunjava uslov: ne govori " + requiredLanguage;
        }

        Double completeness = guideService.getCompleteness(guideId);
        if (completeness != null && completeness < 40.0) {
            return "Vodič " + guide.getFullName() + " ima neadekvatan profil (" + completeness + "%).";
        }

        return "Vodič " + guide.getFullName() + " je uspešno verifikovan za ovu avanturu.";
    }

    public String generateAdventureReport(Long id) {
        Adventure adventure = adventureService.getRawById(id);

        if (adventure.getGuideId() == null) {
            return "Nema dodeljenog vodiča za generisanje izveštaja.";
        }

        Double completeness = guideService.getCompleteness(adventure.getGuideId());
        AdventureReport report = reportFactory.createReport(completeness != null ? completeness : 0.0);
        return report.generate();
    }
}