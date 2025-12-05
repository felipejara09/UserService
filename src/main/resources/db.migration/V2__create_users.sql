CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    document_id VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    role_id INTEGER NOT NULL REFERENCES roles(id),
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL
);

INSERT INTO users (
    name,
    lastname,
    document_id,
    birth_date,
    phone_number,
    role_id,
    email,
    password
) VALUES (
    'Default',
    'Admin',
    '1234567890',
    '1990-01-01',
    '+573001112233',
    1, -- rol ADMIN
    'admin@plazalote.com',
    '$2a$10$VdCkOlVxwl6Dur43UDGbKuM9bHTwXxgK8.G2krTkb2z6IWCgJH3Ay'
);

