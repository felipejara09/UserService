CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO roles(name)
VALUES ('ADMIN'), ('OWNER'), ('EMPLOYED'), ('CLIENT');
