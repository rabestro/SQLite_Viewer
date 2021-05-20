CREATE TABLE IF NOT EXISTS contacts
(
    contact_id INTEGER PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL,
    email      TEXT NOT NULL UNIQUE,
    phone      TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS groups
(
    group_id INTEGER PRIMARY KEY,
    name     TEXT NOT NULL
);

INSERT INTO contacts (contact_id, first_name, last_name, email, phone)
VALUES (1, 'Sharmin', 'Pittman', 'sharmin@gmail.com', '202-555-0140');
INSERT INTO contacts (contact_id, first_name, last_name, email, phone)
VALUES (2, 'Fred', 'Hood', 'fred@gmail.com', '202-555-0175');
INSERT INTO contacts (contact_id, first_name, last_name, email, phone)
VALUES (3, 'Emeli', 'Ortega', 'emeli@gmail.com', '202-555-0138');