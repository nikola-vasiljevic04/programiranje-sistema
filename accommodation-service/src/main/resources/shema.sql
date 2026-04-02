-- Promeni ime u množinu da odgovara importu i slici
CREATE TABLE addresses (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           city VARCHAR(255) NOT NULL,
                           zip_code VARCHAR(20) NOT NULL,
                           street_address VARCHAR(255) NOT NULL
);

CREATE TABLE facilities (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            extra_cost DOUBLE DEFAULT 0.0
);

CREATE TABLE units (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       unit_type VARCHAR(50) NOT NULL,
                       base_price_per_night DOUBLE NOT NULL,
                       address_id BIGINT,
                       CONSTRAINT fk_unit_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);
CREATE TABLE availability_calendar (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       start_date DATE NOT NULL,
                                       end_date DATE NOT NULL,
                                       reason VARCHAR(255),
                                       unit_id BIGINT NOT NULL,
                                       CONSTRAINT fk_calendar_unit FOREIGN KEY (unit_id) REFERENCES units(id) ON DELETE CASCADE
);
CREATE TABLE unit_facilities (
                                 unit_id BIGINT NOT NULL,
                                 facility_id BIGINT NOT NULL,
                                 PRIMARY KEY (unit_id, facility_id),
                                 CONSTRAINT fk_uf_unit FOREIGN KEY (unit_id) REFERENCES units(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_uf_facility FOREIGN KEY (facility_id) REFERENCES facilities(id) ON DELETE CASCADE
);

CREATE TABLE price_tiers (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             multiplier DOUBLE NOT NULL,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             unit_id BIGINT NOT NULL,
                             CONSTRAINT fk_tier_unit FOREIGN KEY (unit_id) REFERENCES units(id) ON DELETE CASCADE
);