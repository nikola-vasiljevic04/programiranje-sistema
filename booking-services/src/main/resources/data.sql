INSERT INTO guests (first_name, last_name, phone_number, email) VALUES
    ('Marko', 'Markovic', '+381641234567', 'marko@email.com'),
    ('Jovana', 'Jovanovic', '+381659876543', 'jovana@email.com');

INSERT INTO cancellation_policies (policy_name, refund_percentage) VALUES
   ('Besplatno 48h ranije', 100),
   ('Povrat 50%', 50);

INSERT INTO bookings (unit_id, start_date, end_date, total_price, status, guest_id, policy_id) VALUES
   (101, '2026-06-01 14:00:00', '2026-06-10 10:00:00', 550.0, 'CONFIRMED', 1, 1),
   (202, '2026-07-15 14:00:00', '2026-07-20 10:00:00', 300.0, 'PENDING', 2, 2);
INSERT INTO add_on_items (service_name, price, booking_id) VALUES
   ('Dorucak', 15.0, 1),
   ('Parking', 10.0, 1),
   ('SPA centar', 40.0, 2);

INSERT INTO booking_status_logs (old_status, new_status, changed_at, booking_id) VALUES
     (NULL, 'PENDING', '2026-01-10 09:00:00', 1),
     ('PENDING', 'CONFIRMED', '2026-01-10 10:30:00', 1),
     (NULL, 'PENDING', '2026-02-15 12:00:00', 2);