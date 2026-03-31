package com.ecotravel.maintenance_service.service;

import com.ecotravel.maintenance_service.dto.WorkOrderRequestDTO;
import com.ecotravel.maintenance_service.dto.WorkOrderResponseDTO;
import com.ecotravel.maintenance_service.mapper.MaintenanceMapper;
import com.ecotravel.maintenance_service.model.*;
import com.ecotravel.maintenance_service.repository.MaintenanceLogRepository;
import com.ecotravel.maintenance_service.repository.MaintenanceTaskRepository;
import com.ecotravel.maintenance_service.repository.WorkOrderRepository;
import com.ecotravel.maintenance_service.repository.WorkerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final WorkerRepository workerRepository;
    private final MaintenanceTaskRepository taskRepository;
    private final MaintenanceLogRepository logRepository;
    private final MaintenanceMapper mapper;

    public WorkOrderService(WorkOrderRepository workOrderRepository, WorkerRepository workerRepository,
                            MaintenanceTaskRepository taskRepository, MaintenanceLogRepository logRepository,
                            MaintenanceMapper mapper) {
        this.workOrderRepository = workOrderRepository;
        this.workerRepository = workerRepository;
        this.taskRepository = taskRepository;
        this.logRepository = logRepository;
        this.mapper = mapper;
    }
    public WorkOrderResponseDTO getWorkOrderById(Long id) {
        WorkOrder order = workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nalog sa ID " + id + " nije pronađen"));
        return mapper.toResponseDTO(order);
    }
    // 1. Kreiranje naloga [cite: 254]
    @Transactional
    public WorkOrderResponseDTO createWorkOrder(WorkOrderRequestDTO dto) {
        Worker worker = workerRepository.findById(dto.getWorkerId())
                .orElseThrow(() -> new EntityNotFoundException("Radnik nije pronađen"));

        MaintenanceTask task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Zadatak nije pronađen"));

        WorkOrder order = new WorkOrder();
        order.setUnitId(dto.getUnitId());
        order.setWorker(worker);
        order.setTask(task);
        order.setScheduledFor(dto.getScheduledFor());
        order.setStatus(WorkOrderStatus.PENDING);

        WorkOrder saved = workOrderRepository.save(order);
        return mapper.toResponseDTO(saved);
    }

    // 2. Dobavljanje sa filtriranjem (Uslov zadatka)
    public List<WorkOrderResponseDTO> getWorkOrders(WorkOrderStatus status) {
        List<WorkOrder> orders;
        if (status != null) {
            orders = workOrderRepository.findByStatus(status);
        } else {
            orders = workOrderRepository.findAll();
        }
        return orders.stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    // 3. Završetak naloga i kreiranje loga (Poslovna logika) [cite: 254]
    @Transactional
    public WorkOrderResponseDTO completeWorkOrder(Long orderId, String notes) {
        WorkOrder order = workOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Nalog nije pronađen"));

        if (order.getStatus() == WorkOrderStatus.COMPLETED) {
            throw new IllegalStateException("Nalog je već završen.");
        }

        order.setStatus(WorkOrderStatus.COMPLETED);
        order.setCompletionDate(LocalDateTime.now());
        WorkOrder updated = workOrderRepository.save(order);

        // Automatsko kreiranje loga kada se nalog završi
        MaintenanceLog log = new MaintenanceLog();
        log.setUnitId(order.getUnitId());
        log.setTaskDescription(order.getTask().getTaskName());
        log.setWorkerName(order.getWorker().getFirstName() + " " + order.getWorker().getLastName());
        log.setCompletedAt(order.getCompletionDate());
        log.setNotes(notes);
        logRepository.save(log);

        return mapper.toResponseDTO(updated);
    }

    // 4. MATEMATIČKA KALKULACIJA (Glavni uslov za odbranu)
    // Računa ukupno preostalo procenjeno vreme u satima za sve aktivne zadatke određenog radnika
    public Integer calculateRemainingWorkloadForWorker(Long workerId) {
        if (!workerRepository.existsById(workerId)) {
            throw new EntityNotFoundException("Radnik nije pronađen");
        }

        List<WorkOrderStatus> activeStatuses = List.of(WorkOrderStatus.PENDING, WorkOrderStatus.IN_PROGRESS);
        List<WorkOrder> activeOrders = workOrderRepository.findByWorkerIdAndStatusIn(workerId, activeStatuses);

        return activeOrders.stream()
                .map(order -> order.getTask().getEstimatedDurationHours())
                .reduce(0, Integer::sum); // Matematičko sumiranje sati
    }
}