package com.ecotravel.mapper;

import com.ecotravel.dtos.CertificationRequestDTO;
import com.ecotravel.dtos.CertificationResponseDTO;
import com.ecotravel.dtos.GuideLanguageDTO;
import com.ecotravel.dtos.GuideProfileRequestDTO;
import com.ecotravel.dtos.GuideProfileResponseDTO;
import com.ecotravel.dtos.LanguageDTO;
import com.ecotravel.model.Certification;
import com.ecotravel.model.GuideLanguage;
import com.ecotravel.model.GuideProfile;
import com.ecotravel.model.Language;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-14T19:40:03+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class GuideMapperImpl implements GuideMapper {

    @Override
    public GuideProfileResponseDTO toResponseDTO(GuideProfile profile) {
        if ( profile == null ) {
            return null;
        }

        GuideProfileResponseDTO guideProfileResponseDTO = new GuideProfileResponseDTO();

        guideProfileResponseDTO.setLanguages( mapLanguages( profile.getGuideLanguages() ) );
        guideProfileResponseDTO.setCertifications( certificationListToCertificationResponseDTOList( profile.getCertifications() ) );
        guideProfileResponseDTO.setId( profile.getId() );
        guideProfileResponseDTO.setBio( profile.getBio() );
        guideProfileResponseDTO.setRating( profile.getRating() );
        guideProfileResponseDTO.setContactEmail( profile.getContactEmail() );
        guideProfileResponseDTO.setCompleteness( profile.getCompleteness() );

        guideProfileResponseDTO.setFullName( profile.getFirstName() + ' ' + profile.getLastName() );

        return guideProfileResponseDTO;
    }

    @Override
    public GuideProfile toEntity(GuideProfileRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GuideProfile guideProfile = new GuideProfile();

        guideProfile.setFirstName( dto.getFirstName() );
        guideProfile.setLastName( dto.getLastName() );
        guideProfile.setBio( dto.getBio() );
        guideProfile.setContactEmail( dto.getContactEmail() );

        guideProfile.setRating( (double) 0.0 );

        return guideProfile;
    }

    @Override
    public CertificationResponseDTO toResponseDTO(Certification cert) {
        if ( cert == null ) {
            return null;
        }

        CertificationResponseDTO certificationResponseDTO = new CertificationResponseDTO();

        certificationResponseDTO.setId( cert.getId() );
        certificationResponseDTO.setName( cert.getName() );
        certificationResponseDTO.setExpiryDate( cert.getExpiryDate() );
        certificationResponseDTO.setIssuingBody( cert.getIssuingBody() );

        certificationResponseDTO.setValid( cert.getExpiryDate() != null && cert.getExpiryDate().isAfter(LocalDate.now()) );

        return certificationResponseDTO;
    }

    @Override
    public Certification toEntity(CertificationRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Certification certification = new Certification();

        certification.setName( dto.getName() );
        certification.setExpiryDate( dto.getExpiryDate() );
        certification.setIssuingBody( dto.getIssuingBody() );

        return certification;
    }

    @Override
    public LanguageDTO toLanguageDTO(Language language) {
        if ( language == null ) {
            return null;
        }

        LanguageDTO languageDTO = new LanguageDTO();

        languageDTO.setId( language.getId() );
        languageDTO.setName( language.getName() );

        return languageDTO;
    }

    @Override
    public Language toLanguageEntity(LanguageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Language language = new Language();

        language.setId( dto.getId() );
        language.setName( dto.getName() );

        return language;
    }

    @Override
    public GuideLanguageDTO toGuideLanguageDto(GuideLanguage gl) {
        if ( gl == null ) {
            return null;
        }

        GuideLanguageDTO guideLanguageDTO = new GuideLanguageDTO();

        guideLanguageDTO.setGuideId( glGuideId( gl ) );
        guideLanguageDTO.setLanguageId( glLanguageId( gl ) );
        guideLanguageDTO.setLanguageName( glLanguageName( gl ) );
        guideLanguageDTO.setId( gl.getId() );

        guideLanguageDTO.setGuideName( gl.getGuide().getFirstName() + ' ' + gl.getGuide().getLastName() );

        return guideLanguageDTO;
    }

    @Override
    public List<GuideLanguageDTO> toGuideLanguageDtoList(List<GuideLanguage> list) {
        if ( list == null ) {
            return null;
        }

        List<GuideLanguageDTO> list1 = new ArrayList<GuideLanguageDTO>( list.size() );
        for ( GuideLanguage guideLanguage : list ) {
            list1.add( toGuideLanguageDto( guideLanguage ) );
        }

        return list1;
    }

    protected List<CertificationResponseDTO> certificationListToCertificationResponseDTOList(List<Certification> list) {
        if ( list == null ) {
            return null;
        }

        List<CertificationResponseDTO> list1 = new ArrayList<CertificationResponseDTO>( list.size() );
        for ( Certification certification : list ) {
            list1.add( toResponseDTO( certification ) );
        }

        return list1;
    }

    private Long glGuideId(GuideLanguage guideLanguage) {
        if ( guideLanguage == null ) {
            return null;
        }
        GuideProfile guide = guideLanguage.getGuide();
        if ( guide == null ) {
            return null;
        }
        Long id = guide.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long glLanguageId(GuideLanguage guideLanguage) {
        if ( guideLanguage == null ) {
            return null;
        }
        Language language = guideLanguage.getLanguage();
        if ( language == null ) {
            return null;
        }
        Long id = language.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String glLanguageName(GuideLanguage guideLanguage) {
        if ( guideLanguage == null ) {
            return null;
        }
        Language language = guideLanguage.getLanguage();
        if ( language == null ) {
            return null;
        }
        String name = language.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
