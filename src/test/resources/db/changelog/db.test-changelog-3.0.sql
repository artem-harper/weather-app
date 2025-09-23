--liquibase formatted sql

--changeset locationTest:1
CREATE TABLE IF NOT EXISTS LOCATIONS
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    user_id INT NOT NULL,
    latitude DECIMAL NOT NULL,
    longitude DECIMAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--changeset locationTest:2
INSERT INTO LOCATIONS (name, user_id, latitude, longitude) VALUES ('Москва', 1, 55.755826, 37.617299)
