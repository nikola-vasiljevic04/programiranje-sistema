package com.ecotravel.accommodation_service.repository;

import com.ecotravel.accommodation_service.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query("SELECT u FROM Unit u JOIN u.address a WHERE u.unitType = :type AND a.city = :city")
    List<Unit> findUnitsByTypeAndCity(@Param("type") String type, @Param("city") String city);
    @Query("SELECT AVG(u.basePricePerNight) FROM Unit u WHERE u.unitType = :type")
    Double getAveragePriceForUnitType(@Param("type") String type);
    @Query("SELECT u FROM Unit u JOIN u.facilities f WHERE f.name = :facilityName")
    List<Unit> findUnitsByFacilityName(@Param("facilityName") String facilityName);
}
