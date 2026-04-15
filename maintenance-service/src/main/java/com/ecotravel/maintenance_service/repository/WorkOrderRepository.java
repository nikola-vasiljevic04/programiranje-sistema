package com.ecotravel.maintenance_service.repository;
import com.ecotravel.maintenance_service.model.WorkOrder;
import com.ecotravel.maintenance_service.model.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByStatus(WorkOrderStatus status);
    List<WorkOrder> findByWorkerIdAndStatusIn(Long workerId, List<WorkOrderStatus> statuses);
}
