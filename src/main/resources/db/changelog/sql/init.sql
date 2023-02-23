--liquibase formatted sql

--changeset truemabadi:1
CREATE TABLE IF NOT EXISTS guests
(
    id		BIGSERIAL PRIMARY KEY,
    telegram_id	VARCHAR,
    last_visit 	TIMESTAMP,
    last_menu	INT
);

--changeset truemabadi:2
CREATE TABLE IF NOT EXISTS adopters
(
    id		BIGSERIAL PRIMARY KEY,
    first_name	VARCHAR,
    last_name	VARCHAR,
    passport	VARCHAR,
    age		INT,
    phone1	VARCHAR,
    phone2	VARCHAR,
    email	VARCHAR,
    telegram	VARCHAR, // telegram id
    volunteer_id INT, // Lookup to [volunteers] table
    on_probation BOOL,
    active	BOOL
);

--changeset itamerlan:3
CREATE TABLE IF NOT EXISTS volunteers
(
    id		SERIAL PRIMARY KEY,
    name	VARCHAR,
    telegram	VARCHAR,
    picture	BYTEA
);

--changeset itamerlan:4
CREATE TABLE IF NOT EXISTS adoption_reports
(
    adopter_id	BIGSERIAL PRIMARY KEY,
    report_date TIMESTAMP,
    pet_id	INT,
    picture	BYTEA,
    diet	VARCHAR,
    wellbeing	VARCHAR,
    behavior_change VARCHAR
)