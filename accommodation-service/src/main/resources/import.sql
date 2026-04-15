-- 1. Unos adresa
INSERT INTO addresses (city, zip_code, street_address) VALUES ('Zlatibor', '31315', 'Kraljeve Vode bb');
INSERT INTO addresses (city, zip_code, street_address) VALUES ('Kopaonik', '36354', 'Sunčani vrhovi 1');
INSERT INTO addresses (city, zip_code, street_address) VALUES ('Tara', '31250', 'Mitrovac bb');

-- 2. Unos sadržaja (facilities)
INSERT INTO facilities (name, extra_cost) VALUES ('WiFi', 0.0);
INSERT INTO facilities (name, extra_cost) VALUES ('Bazen', 25.0);
INSERT INTO facilities (name, extra_cost) VALUES ('Sauna', 15.0);
INSERT INTO facilities (name, extra_cost) VALUES ('Doručak', 10.0);

-- 3. Unos smeštajnih jedinica (povezivanje sa address_id)
INSERT INTO units (name, unit_type, base_price_per_night, address_id) VALUES ('Planinska Idila', 'CABIN', 120.0, 1);
INSERT INTO units (name, unit_type, base_price_per_night, address_id) VALUES ('Grand Apartman', 'APARTMENT', 200.0, 2);
INSERT INTO units (name, unit_type, base_price_per_night, address_id) VALUES ('Šumska Kuća', 'HOUSE', 90.0, 3);
-- 4. Povezivanje smeštaja i sadržaja (unit_facilities spojna tabela)
-- Smeštaj 1 (Idila) ima WiFi (1) i Saunu (3)
INSERT INTO unit_facilities (unit_id, facility_id) VALUES (1, 1);
INSERT INTO unit_facilities (unit_id, facility_id) VALUES (1, 3);
-- Smeštaj 2 (Grand) ima WiFi (1), Bazen (2) i Doručak (4)
INSERT INTO unit_facilities (unit_id, facility_id) VALUES (2, 1);
INSERT INTO unit_facilities (unit_id, facility_id) VALUES (2, 2);
INSERT INTO unit_facilities (unit_id, facility_id) VALUES (2, 4);

INSERT INTO availability_calendar (unit_id, start_date, end_date, reason)
VALUES (1, '2026-03-01', '2026-03-31', 'Godišnji odmor');
-- Rezervacija u junu
INSERT INTO availability_calendar (unit_id, start_date, end_date, reason)
VALUES (1, '2026-06-15', '2026-06-25', 'Rezervacija #456');

-- 5. Unos sezonskih cena (price_tiers)
INSERT INTO price_tiers (name, multiplier, start_date, end_date, unit_id) VALUES ('Zimska sezona', 1.5, '2026-12-01', '2027-02-28', 1);
INSERT INTO price_tiers (name, multiplier, start_date, end_date, unit_id) VALUES ('Zimska sezona', 1.8, '2026-12-01', '2027-02-28', 2);