package com.ecotravel.maintenance_service.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_logs")
@Getter @Setter
@NoArgsConstructor
public class MaintenanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Column(name = "task_description", nullable = false)
    private String taskDescription;

    @Column(name = "worker_name", nullable = false)
    private String workerName;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
