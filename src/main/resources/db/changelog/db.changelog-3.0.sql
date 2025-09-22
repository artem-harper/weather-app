--liquibase formatted sql

--changeset location:1
CREATE TABLE IF NOT EXISTS Locations
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    user_id INT REFERENCES users(id) NOT NULL,
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL
)