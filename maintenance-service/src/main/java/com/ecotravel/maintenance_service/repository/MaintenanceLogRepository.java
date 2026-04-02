package com.ecotravel.maintenance_service.repository;
import com.ecotravel.maintenance_service.model.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {
    List<MaintenanceLog> findByUnitId(Long unitId);
}
