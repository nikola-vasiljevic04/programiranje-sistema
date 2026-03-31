CREATE TABLE IF NOT EXISTS workers (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS maintenance_tasks (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 task_name VARCHAR(255) NOT NULL,
    estimated_duration_hours INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS work_orders (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           unit_id BIGINT NOT NULL,
                                           worker_id BIGINT NOT NULL,
                                           task_id BIGINT NOT NULL,
                                           status VARCHAR(50) NOT NULL,
    scheduled_for TIMESTAMP,
    completion_date TIMESTAMP,
    CONSTRAINT fk_wo_worker FOREIGN KEY (worker_id) REFERENCES workers(id),
    CONSTRAINT fk_wo_task FOREIGN KEY (task_id) REFERENCES maintenance_tasks(id)
    );

CREATE TABLE IF NOT EXISTS maintenance_logs (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                unit_id BIGINT NOT NULL,
                                                task_description VARCHAR(255) NOT NULL,
    worker_name VARCHAR(255) NOT NULL,
    completed_at TIMESTAMP NOT NULL,
    notes TEXT
    );