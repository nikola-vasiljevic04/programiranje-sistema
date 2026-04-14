INSERT INTO users (first_name, last_name, email, hashed_password)
VALUES ('Nikola', 'Nedeljkovic', 'admin@raflab.org', '$2a$12$l8flmY1aY.AFIimlbkByN.2vOIn/Pk2ar/PS66VDGSfzhtPogST8S');

INSERT INTO user_roles (user_id, role) VALUES (1, 'ADMIN');
INSERT INTO user_roles (user_id, role) VALUES (1, 'GUEST');