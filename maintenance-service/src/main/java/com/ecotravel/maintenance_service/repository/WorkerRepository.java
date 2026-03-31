package com.ecotravel.maintenance_service.repository;
import com.ecotravel.maintenance_service.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {}
