-- 1. Očisti tabele (Redosled je bitan zbog Foreign Key ograničenja)
DELETE FROM guide_languages;
DELETE FROM certifications;
DELETE FROM guide_profiles;
DELETE FROM languages;

-- 2. JEZICI
-- Dobiće automatski: Engleski (1), Italijanski (2), Francuski (3)
INSERT INTO languages (name) VALUES ('Engleski');
INSERT INTO languages (name) VALUES ('Italijanski');
INSERT INTO languages (name) VALUES ('Francuski');

-- 3. VODIČI
-- Dobiće automatski: Milan (1), Jelena (2)
INSERT INTO guide_profiles (first_name, last_name, bio, rating, contact_email, completeness)
VALUES ('Milan', 'Stojičević', 'Iskusni planinar sa 10 godina iskustva u Alpima.', 4.9, 'milan@ecotravel.com', 85.0);

INSERT INTO guide_profiles (first_name, last_name, bio, rating, contact_email, completeness)
VALUES ('Jelena', 'Simić', 'Specijalista za pešačke ture i botaničke rezervate.', 4.7, 'jelena@ecotravel.com', 90.0);

-- 4. VEZA VODIČ - JEZIK
-- Ovde moramo znati koji ID je baza dodelila.
-- Pošto su ovo prvi unosi, Milan je 1, Jelena je 2, Engleski je 1, Italijanski je 2.
INSERT INTO guide_languages (guide_id, language_id) VALUES (1, 1); -- Milan govori Engleski
INSERT INTO guide_languages (guide_id, language_id) VALUES (2, 2); -- Jelena govori Italijanski

-- 5. SERTIFIKATI
-- Milan (ID 1) dobija sertifikat
INSERT INTO certifications (name, issuing_body, expiry_date, guide_id)
VALUES ('Gorska služba spasavanja', 'GSS Srbije', '2027-05-20', 1);