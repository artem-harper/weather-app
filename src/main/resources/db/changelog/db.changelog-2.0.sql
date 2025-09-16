--formatted sql

--changeset artem:1
CREATE TABLE IF NOT EXISTS Sessions
(
    id UUID PRIMARY KEY,
    user_id INT REFERENCES Users(id) NOT NULL,
    expires_at TIMESTAMP NOT NULL
)
