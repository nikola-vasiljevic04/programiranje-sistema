package com.ecotravel.mapper;

import com.ecotravel.dtos.AdventureCategoryDTO;
import com.ecotravel.dtos.AdventureRequestDTO;
import com.ecotravel.dtos.AdventureResponseDTO;
import com.ecotravel.dtos.AdventureReviewRequestDTO;
import com.ecotravel.dtos.AdventureReviewResponseDTO;
import com.ecotravel.dtos.GearRequirementDTO;
import com.ecotravel.dtos.TimeSlotRequestDTO;
import com.ecotravel.dtos.TimeSlotResponseDTO;
import com.ecotravel.model.Adventure;
import com.ecotravel.model.AdventureCategory;
import com.ecotravel.model.AdventureReview;
import com.ecotravel.model.GearRequirement;
import com.ecotravel.model.TimeSlot;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-15T23:23:08+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class AdventureMapperImpl implements AdventureMapper {

    @Override
    public AdventureResponseDTO toResponseDTO(Adventure adventure) {
        if ( adventure == null ) {
            return null;
        }

        AdventureResponseDTO adventureResponseDTO = new AdventureResponseDTO();

        adventureResponseDTO.setCategoryName( adventureCategoryName( adventure ) );
        adventureResponseDTO.setId( adventure.getId() );
        adventureResponseDTO.setTitle( adventure.getTitle() );
        adventureResponseDTO.setDescription( adventure.getDescription() );
        adventureResponseDTO.setDifficultyLevel( adventure.getDifficultyLevel() );
        adventureResponseDTO.setBasePrice( adventure.getBasePrice() );

        return adventureResponseDTO;
    }

    @Override
    public Adventure toEntity(AdventureRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Adventure adventure = new Adventure();

        adventure.setTitle( dto.getTitle() );
        adventure.setDescription( dto.getDescription() );
        adventure.setDifficultyLevel( dto.getDifficultyLevel() );
        adventure.setBasePrice( dto.getBasePrice() );
        adventure.setGuideId( dto.getGuideId() );

        return adventure;
    }

    @Override
    public AdventureCategoryDTO toDTO(AdventureCategory category) {
        if ( category == null ) {
            return null;
        }

        AdventureCategoryDTO adventureCategoryDTO = new AdventureCategoryDTO();

        adventureCategoryDTO.setId( category.getId() );
        adventureCategoryDTO.setName( category.getName() );
        adventureCategoryDTO.setDescription( category.getDescription() );

        return adventureCategoryDTO;
    }

    @Override
    public AdventureCategory toEntity(AdventureCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AdventureCategory adventureCategory = new AdventureCategory();

        adventureCategory.setName( dto.getName() );
        adventureCategory.setDescription( dto.getDescription() );

        return adventureCategory;
    }

    @Override
    public TimeSlotResponseDTO toResponseDTO(TimeSlot timeSlot) {
        if ( timeSlot == null ) {
            return null;
        }

        TimeSlotResponseDTO timeSlotResponseDTO = new TimeSlotResponseDTO();

        timeSlotResponseDTO.setAdventureTitle( timeSlotAdventureTitle( timeSlot ) );
        timeSlotResponseDTO.setId( timeSlot.getId() );
        timeSlotResponseDTO.setStartTime( timeSlot.getStartTime() );
        timeSlotResponseDTO.setEndTime( timeSlot.getEndTime() );
        timeSlotResponseDTO.setMaxParticipants( timeSlot.getMaxParticipants() );
        timeSlotResponseDTO.setCurrentOccupancy( timeSlot.getCurrentOccupancy() );

        return timeSlotResponseDTO;
    }

    @Override
    public TimeSlot toEntity(TimeSlotRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setStartTime( dto.getStartTime() );
        timeSlot.setEndTime( dto.getEndTime() );
        timeSlot.setMaxParticipants( dto.getMaxParticipants() );

        timeSlot.setCurrentOccupancy( 0 );

        return timeSlot;
    }

    @Override
    public GearRequirementDTO toDTO(GearRequirement gear) {
        if ( gear == null ) {
            return null;
        }

        GearRequirementDTO gearRequirementDTO = new GearRequirementDTO();

        gearRequirementDTO.setAdventureId( gearAdventureId( gear ) );
        gearRequirementDTO.setId( gear.getId() );
        gearRequirementDTO.setItemName( gear.getItemName() );
        gearRequirementDTO.setIsMandatory( gear.getIsMandatory() );

        return gearRequirementDTO;
    }

    @Override
    public GearRequirement toEntity(GearRequirementDTO dto) {
        if ( dto == null ) {
            return null;
        }

        GearRequirement gearRequirement = new GearRequirement();

        gearRequirement.setItemName( dto.getItemName() );
        gearRequirement.setIsMandatory( dto.getIsMandatory() );

        return gearRequirement;
    }

    @Override
    public AdventureReviewResponseDTO toResponseDTO(AdventureReview review) {
        if ( review == null ) {
            return null;
        }

        AdventureReviewResponseDTO adventureReviewResponseDTO = new AdventureReviewResponseDTO();

        adventureReviewResponseDTO.setAdventureTitle( reviewAdventureTitle( review ) );
        adventureReviewResponseDTO.setId( review.getId() );
        adventureReviewResponseDTO.setRating( review.getRating() );
        adventureReviewResponseDTO.setComment( review.getComment() );
        adventureReviewResponseDTO.setCreatedAt( review.getCreatedAt() );

        return adventureReviewResponseDTO;
    }

    @Override
    public AdventureReview toEntity(AdventureReviewRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AdventureReview adventureReview = new AdventureReview();

        adventureReview.setRating( dto.getRating() );
        adventureReview.setComment( dto.getComment() );

        return adventureReview;
    }

    private String adventureCategoryName(Adventure adventure) {
        AdventureCategory category = adventure.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getName();
    }

    private String timeSlotAdventureTitle(TimeSlot timeSlot) {
        Adventure adventure = timeSlot.getAdventure();
        if ( adventure == null ) {
            return null;
        }
        return adventure.getTitle();
    }

    private Long gearAdventureId(GearRequirement gearRequirement) {
        Adventure adventure = gearRequirement.getAdventure();
        if ( adventure == null ) {
            return null;
        }
        return adventure.getId();
    }

    private String reviewAdventureTitle(AdventureReview adventureReview) {
        Adventure adventure = adventureReview.getAdventure();
        if ( adventure == null ) {
            return null;
        }
        return adventure.getTitle();
    }
}
