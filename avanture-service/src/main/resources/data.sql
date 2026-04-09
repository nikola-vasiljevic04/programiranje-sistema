-- 1. KATEGORIJE
INSERT INTO adventure_categories (id, name, description)
VALUES (1, 'Planinarenje', 'Osvajanje planinskih vrhova i šetnje kroz prirodu.');

INSERT INTO adventure_categories (id, name, description)
VALUES (2, 'Vodeni sportovi', 'Adrenalinsko spuštanje rekama i vožnja kajakom.');

-- 2. AVANTURE
INSERT INTO adventures (id, category_id, guide_id, title, description, difficulty_level, base_price)
VALUES (1, 1, 1, 'Uspon na Rtanj', 'Mistična tura na planinu piramidalnog oblika.', 'MEDIUM', 2500.0);

INSERT INTO adventures (id, category_id, guide_id, title, description, difficulty_level, base_price)
VALUES (2, 1, 2, 'Pešačenje Fruškom Gorom', 'Lagana tura kroz nacionalni park.', 'EASY', 1500.0);

INSERT INTO adventures (id, category_id, guide_id, title, description, difficulty_level, base_price)
VALUES (3, 2, 3, 'Rafting na Tari', 'Nezaboravna avantura na suzi Evrope.', 'HARD', 8500.0);

-- 3. TERMINI (ovde verovatno imaš auto-increment ID, pa ne navodimo ID ručno)
INSERT INTO time_slots (adventure_id, start_time, end_time, max_participants, current_occupancy)
VALUES (1, '2026-05-15 08:00:00', '2026-05-15 18:00:00', 20, 0);

INSERT INTO time_slots (adventure_id, start_time, end_time, max_participants, current_occupancy)
VALUES (3, '2026-06-10 10:00:00', '2026-06-12 18:00:00', 10, 0);

-- 4. OPREMA
INSERT INTO gear_requirements (adventure_id, item_name, is_mandatory)
VALUES (1, 'Planinarske cipele', true);

INSERT INTO gear_requirements (adventure_id, item_name, is_mandatory)
VALUES (3, 'Kaciga', true);

INSERT INTO gear_requirements (adventure_id, item_name, is_mandatory)
VALUES (3, 'Prsluk za spasavanje', true);

-- 5. RECENZIJE
INSERT INTO adventure_reviews (adventure_id, rating, comment, created_at)
VALUES (1, 5, 'Sjajno iskustvo, prelep pogled!', '2026-04-01 10:00:00');

-- ==========================================================
-- UNIVERZALNA POPRAVKA ZA BROJAČE (ID-EVE)
-- Ovo osigurava da Swagger POST komande ne udaraju u UniqueConstraint grešku
-- ==========================================================

-- Sinhronizuj ID za kategorije (sledeći će biti 3)
ALTER TABLE adventure_categories ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM adventure_categories);

-- Sinhronizuj ID za avanture (sledeći će biti 4)
ALTER TABLE adventures ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM adventures);

-- Ako tvoje tabele za termine, opremu ili recenzije imaju ID kolone koje nisi naveo iznad,
-- Hibernate će ih sam popunjavati od 1, što je OK jer ih gore nisi ručno "zauzeo".