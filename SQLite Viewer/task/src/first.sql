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

