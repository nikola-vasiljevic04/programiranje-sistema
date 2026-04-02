package com.ecotravel.maintenance_service.repository;
import com.ecotravel.maintenance_service.model.WorkOrder;
import com.ecotravel.maintenance_service.model.WorkOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    // Ovo će nam trebati za GET endpoint sa filtriranjem
    List<WorkOrder> findByStatus(WorkOrderStatus status);

    // Ovo će nam trebati za metodu sa matematičkom kalkulacijom (računanje opterećenja radnika)
    List<WorkOrder> findByWorkerIdAndStatusIn(Long workerId, List<WorkOrderStatus> statuses);
}
