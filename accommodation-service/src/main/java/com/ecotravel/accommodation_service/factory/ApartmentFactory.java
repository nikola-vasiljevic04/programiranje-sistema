package com.ecotravel.accommodation_service.factory;

import com.ecotravel.accommodation_service.dto.UnitRequestDTO;
import com.ecotravel.accommodation_service.model.Unit;
import org.springframework.stereotype.Component;

@Component
public class ApartmentFactory implements UnitFactory{
    @Override
    public Unit createUnit(UnitRequestDTO unitRequestDTO) {
        Unit unit = new Unit();
        unit.setName(unitRequestDTO.getName());
        unit.setBasePricePerNight(unit.getBasePricePerNight());
        unit.setUnitType("APARTMENT");
        return unit;
    }

    @Override
    public String getUnitType() {
        return "APARTMENT";
    }
}
