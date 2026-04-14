package com.ecotravel.mapper;

import com.ecotravel.dtos.*;
import com.ecotravel.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdventureMapper {

    @Mapping(source = "category.name", target = "categoryName")
    AdventureResponseDTO toResponseDTO(Adventure adventure);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Adventure toEntity(AdventureRequestDTO dto);

    AdventureCategoryDTO toDTO(AdventureCategory category);

    @Mapping(target = "id", ignore = true)
    AdventureCategory toEntity(AdventureCategoryDTO dto);

    @Mapping(source = "adventure.title", target = "adventureTitle")
    TimeSlotResponseDTO toResponseDTO(TimeSlot timeSlot);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adventure", ignore = true)
    @Mapping(target = "currentOccupancy", constant = "0")
    TimeSlot toEntity(TimeSlotRequestDTO dto);

    @Mapping(source = "adventure.id", target = "adventureId")
    GearRequirementDTO toDTO(GearRequirement gear);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adventure", ignore = true)
    GearRequirement toEntity(GearRequirementDTO dto);

    @Mapping(source = "adventure.title", target = "adventureTitle")
    AdventureReviewResponseDTO toResponseDTO(AdventureReview review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adventure", ignore = true)
    AdventureReview toEntity(AdventureReviewRequestDTO dto);
}