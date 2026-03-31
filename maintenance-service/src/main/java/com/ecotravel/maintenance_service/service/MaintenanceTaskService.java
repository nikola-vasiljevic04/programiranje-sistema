package com.ecotravel.maintenance_service.service;

import com.ecotravel.maintenance_service.dto.MaintenanceTaskRequestDTO;
import com.ecotravel.maintenance_service.dto.MaintenanceTaskResponseDTO;
import com.ecotravel.maintenance_service.mapper.MaintenanceMapper;
import com.ecotravel.maintenance_service.model.MaintenanceTask;
import com.ecotravel.maintenance_service.repository.MaintenanceTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceTaskService {

    private final MaintenanceTaskRepository taskRepository;
    private final MaintenanceMapper mapper;

    public MaintenanceTaskService(MaintenanceTaskRepository taskRepository, MaintenanceMapper mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    public List<MaintenanceTaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MaintenanceTaskResponseDTO createTask(MaintenanceTaskRequestDTO dto) {
        MaintenanceTask task = mapper.toEntity(dto);
        MaintenanceTask saved = taskRepository.save(task);
        return mapper.toResponseDTO(saved);
    }
}