package com.ecotravel.maintenance_service.service;

import com.ecotravel.maintenance_service.dto.WorkerRequestDTO;
import com.ecotravel.maintenance_service.dto.WorkerResponseDTO;
import com.ecotravel.maintenance_service.mapper.MaintenanceMapper;
import com.ecotravel.maintenance_service.model.Worker;
import com.ecotravel.maintenance_service.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final MaintenanceMapper mapper;

    public WorkerService(WorkerRepository workerRepository, MaintenanceMapper mapper) {
        this.workerRepository = workerRepository;
        this.mapper = mapper;
    }

    public List<WorkerResponseDTO> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public WorkerResponseDTO createWorker(WorkerRequestDTO dto) {
        Worker worker = mapper.toEntity(dto);
        Worker saved = workerRepository.save(worker);
        return mapper.toResponseDTO(saved);
    }
}