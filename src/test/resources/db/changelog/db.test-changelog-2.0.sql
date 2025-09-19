--liquibase formatted sql

--changeset test:1
CREATE TABLE IF NOT EXISTS sessions (
    id UUID PRIMARY KEY,                 -- Native UUID тип
    user_id INTEGER NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_sessions_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

