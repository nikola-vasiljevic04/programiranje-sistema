package com.ecotravel.mapper;

import com.ecotravel.dtos.*;
import com.ecotravel.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdventureMapper {

    // --- ADVENTURE ---
    // Ovde namerno NE dodajemo mapiranje za guide, jer to radi Facade ručno
    @Mapping(source = "category.name", target = "categoryName")
    // NE mapiramo guide polje ovde!
    AdventureResponseDTO toResponseDTO(Adventure adventure);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
        // guideId se automatski mapira ako se polja u DTO i Entity zovu isto
    Adventure toEntity(AdventureRequestDTO dto);

    // --- CATEGORY ---
    AdventureCategoryDTO toDTO(AdventureCategory category);

    @Mapping(target = "id", ignore = true)
    AdventureCategory toEntity(AdventureCategoryDTO dto);

    // --- TIME SLOT ---
    @Mapping(source = "adventure.title", target = "adventureTitle")
    TimeSlotResponseDTO toResponseDTO(TimeSlot timeSlot);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adventure", ignore = true)
    @Mapping(target = "currentOccupancy", constant = "0")
    TimeSlot toEntity(TimeSlotRequestDTO dto);

    // --- GEAR REQUIREMENT ---
    @Mapping(source = "adventure.id", target = "adventureId")
    GearRequirementDTO toDTO(GearRequirement gear);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adventure", ignore = true)
    GearRequirement toEntity(GearRequirementDTO dto);

    // --- REVIEW ---
    @Mapping(source = "adventure.title", target = "adventureTitle")
    AdventureReviewResponseDTO toResponseDTO(AdventureReview review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "adventure", ignore = true)
    AdventureReview toEntity(AdventureReviewRequestDTO dto);
}