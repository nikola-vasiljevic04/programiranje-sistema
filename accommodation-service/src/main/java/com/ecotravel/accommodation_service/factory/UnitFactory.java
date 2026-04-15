package com.ecotravel.accommodation_service.factory;

import com.ecotravel.accommodation_service.dto.UnitRequestDTO;
import com.ecotravel.accommodation_service.model.Unit;

public interface UnitFactory {
    Unit createUnit(UnitRequestDTO unitRequestDTO);
    String getUnitType();
}
