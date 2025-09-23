--liquibase formatted sql

--changeset testSession:1
create TABLE IF NOT EXISTS sessions (
    id UUID PRIMARY KEY,
    user_id INTEGER NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_sessions_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON delete CASCADE
);

--changeset testSession:2
insert into sessions (id, user_id, expires_at) values ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1, '2024-12-31 23:59:59');
