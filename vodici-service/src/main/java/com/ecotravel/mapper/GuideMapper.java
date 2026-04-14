package com.ecotravel.mapper;

import com.ecotravel.dtos.*;
import com.ecotravel.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {java.time.LocalDate.class, java.util.stream.Collectors.class, java.util.ArrayList.class})
public interface GuideMapper {


    @Mapping(target = "fullName", expression = "java(profile.getFirstName() + ' ' + profile.getLastName())")
    @Mapping(target = "languages", source = "guideLanguages") // MapStruct će koristiti default metodu mapLanguages ispod
    @Mapping(target = "certifications", source = "certifications")
    GuideProfileResponseDTO toResponseDTO(GuideProfile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guideLanguages", ignore = true)
    @Mapping(target = "certifications", ignore = true)
    @Mapping(target = "rating", constant = "0.0")
    GuideProfile toEntity(GuideProfileRequestDTO dto);


    @Mapping(target = "valid", expression = "java(cert.getExpiryDate() != null && cert.getExpiryDate().isAfter(LocalDate.now()))")
    CertificationResponseDTO toResponseDTO(Certification cert);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guide", ignore = true)
    Certification toEntity(CertificationRequestDTO dto);


    default List<String> mapLanguages(List<GuideLanguage> guideLanguages) {
        if (guideLanguages == null) return new ArrayList<>();
        return guideLanguages.stream()
                .filter(gl -> gl.getLanguage() != null)
                .map(gl -> gl.getLanguage().getName())
                .collect(Collectors.toList());
    }

    default String mapGuideLanguageToString(GuideLanguage gl) {
        if (gl == null || gl.getLanguage() == null) return null;
        return gl.getLanguage().getName();
    }


    LanguageDTO toLanguageDTO(Language language);
    Language toLanguageEntity(LanguageDTO dto);

    @Mapping(target = "guideId", source = "guide.id")
    @Mapping(target = "languageId", source = "language.id")
    @Mapping(target = "languageName", source = "language.name")
    @Mapping(target = "guideName", expression = "java(gl.getGuide().getFirstName() + ' ' + gl.getGuide().getLastName())")
    GuideLanguageDTO toGuideLanguageDto(GuideLanguage gl);

    List<GuideLanguageDTO> toGuideLanguageDtoList(List<GuideLanguage> list);
}