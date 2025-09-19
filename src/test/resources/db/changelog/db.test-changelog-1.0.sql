--liquibase formatted sql

--changeset artem:1
CREATE TABLE USERS
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL
);

--changeset artem:2
insert into Users(login, password) values ('liquibaseUser', '1234');


