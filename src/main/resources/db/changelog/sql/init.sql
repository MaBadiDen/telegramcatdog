--liquibase formatted sql

--changeset truemabadi:1
CREATE TABLE IF NOT EXISTS guests
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    telegram_id
    VARCHAR,
    last_visit
    TIMESTAMP,
    last_menu
    INT
);

--changeset truemabadi:2
CREATE TABLE IF NOT EXISTS adopters
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    first_name
    VARCHAR,
    last_name
    VARCHAR,
    passport
    VARCHAR,
    age
    INT,
    phone1
    VARCHAR,
    phone2
    VARCHAR,
    email
    VARCHAR,
    telegram
    VARCHAR, -- telegram id
    volunteer_id
    INT,     -- Lookup to [volunteers] table
    on_probation
    BOOL,
    active
    BOOL
);

--changeset itamerlan:3
CREATE TABLE IF NOT EXISTS volunteers
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR,
    telegram
    VARCHAR,
    picture
    BYTEA
);

--changeset itamerlan:4
CREATE TABLE IF NOT EXISTS adoption_reports
(
    adopter_id
    BIGSERIAL
    PRIMARY
    KEY,
    report_date
    TIMESTAMP,
    pet_id
    INT,
    picture
    BYTEA,
    diet
    VARCHAR,
    wellbeing
    VARCHAR,
    behavior_change
    VARCHAR
)

-- changeset alexeym75:1
CREATE TABLE IF NOT EXISTS branch_params
(
    id
    INT
    PRIMARY
    KEY,
    name
    VARCHAR,
    country
    VARCHAR,
    city
    VARCHAR,
    zip
    VARCHAR,
    address
    VARCHAR,
    work_hours
    VARCHAR,
    map
    BYTEA, -- directions how to get there
    info
    TEXT,  -- other information
    prob_period
    INT,   -- probation period (in days)
    prob_extend
    INT    -- probation extension period (in days)
);

-- changeset alexeym75:2
CREATE TABLE IF NOT EXISTS pets
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    nick_name
    VARCHAR,
    pet_type
    INT, -- enum PetType
    --breed_id	INT, -- lookup to [breeds] table
    color
    INT, -- enum Color
    sex
    INT  -- enum Sex
    --picture     BYTEA,
    --adopter_id  INT  -- lookup to [adopters] table
)

-- changeset alexeym75:3
ALTER TABLE volunteers DROP COLUMN telegram;
ALTER TABLE volunteers
    ADD COLUMN telegram_chat_id BIGINT;
ALTER TABLE volunteers
    ADD COLUMN telegram_username VARCHAR;
