package com.ecotravel.accommodation_service.factory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UnitFactoryRegistry {
    private final Map<String, UnitFactory> factories = new HashMap<>();
    public UnitFactoryRegistry(List<UnitFactory> factoryList) {
        for (UnitFactory factory : factoryList) {
            factories.put(factory.getUnitType().toUpperCase(), factory);
        }
    }
    public UnitFactory getFactory(String type) {
        UnitFactory factory = factories.get(type.toUpperCase());
        if (factory == null) {
            throw new IllegalArgumentException("Nepoznat tip smeštaja: " + type);
        }
        return factory;
    }
}
