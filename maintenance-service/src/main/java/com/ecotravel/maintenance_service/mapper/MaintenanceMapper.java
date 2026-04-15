package com.ecotravel.maintenance_service.mapper;

import com.ecotravel.maintenance_service.dto.*;
import com.ecotravel.maintenance_service.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    Worker toEntity(WorkerRequestDTO dto);
    WorkerResponseDTO toResponseDTO(Worker entity);

    MaintenanceTask toEntity(MaintenanceTaskRequestDTO dto);
    MaintenanceTaskResponseDTO toResponseDTO(MaintenanceTask entity);
    @Mapping(target = "workerFullName", expression = "java(entity.getWorker().getFirstName() + \" \" + entity.getWorker().getLastName())")
    @Mapping(source = "task.taskName", target = "taskName")
    @Mapping(source = "task.estimatedDurationHours", target = "estimatedDurationHours")
    @Mapping(source = "worker.id", target = "workerId")
    WorkOrderResponseDTO toResponseDTO(WorkOrder entity);

    MaintenanceLogResponseDTO toResponseDTO(MaintenanceLog entity);
}