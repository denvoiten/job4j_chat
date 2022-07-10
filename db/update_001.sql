CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS persons (
    id SERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(1000),
    password VARCHAR(1000),
    role_id INT REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY NOT NULL,
    text TEXT,
    created TIMESTAMP,
    person_id INT REFERENCES persons(id),
    room_id INT REFERENCES rooms(id)
);

INSERT INTO roles(name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO persons(login, password, role_id) VALUES ('user', 'user', 1), ('admin', 'admin', 2);
INSERT INTO rooms(name) VALUES ('first'), ('second');
INSERT INTO messages(text, created, person_id, room_id)
VALUES ('bla bla bla', now(), 2, 1), ('Some text', now(), 1, 2);
