INSERT INTO workers (first_name, last_name, specialty) VALUES ('Jovan', 'Jovanović', 'Električar');
INSERT INTO workers (first_name, last_name, specialty) VALUES ('Petar', 'Petrović', 'Vodoinstalater');
INSERT INTO workers (first_name, last_name, specialty) VALUES ('Ana', 'Anić', 'Higijeničar');

INSERT INTO maintenance_tasks (task_name, estimated_duration_hours) VALUES ('Popravka klime', 3);
INSERT INTO maintenance_tasks (task_name, estimated_duration_hours) VALUES ('Generalno čišćenje', 4);
INSERT INTO maintenance_tasks (task_name, estimated_duration_hours) VALUES ('Zavrtanje ventila', 1);

INSERT INTO work_orders (unit_id, worker_id, task_id, status, scheduled_for) VALUES (1, 1, 1, 'PENDING', '2026-04-10 10:00:00');
INSERT INTO work_orders (unit_id, worker_id, task_id, status, scheduled_for) VALUES (2, 3, 2, 'IN_PROGRESS', '2026-04-12 08:00:00');