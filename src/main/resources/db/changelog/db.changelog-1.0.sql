--liquibase formatted sql

--changeset artem:1
create TABLE Users
(
id SERIAL PRIMARY KEY,
login VARCHAR(128) NOT NULL unique,
password VARCHAR(128) NOT NULL
)

--changeset artem:2
insert into Users(login, password) values ('pidoras', '1234');