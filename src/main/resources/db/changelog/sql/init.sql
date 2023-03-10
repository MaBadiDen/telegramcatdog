--liquibase formatted sql

--changeset truemabadi:1
CREATE TABLE IF NOT EXISTS guests
(
    id          BIGSERIAL PRIMARY KEY,
    telegram_id VARCHAR,
    last_visit  TIMESTAMP,
    last_menu   INT
);

--changeset truemabadi:2
CREATE TABLE IF NOT EXISTS adopters
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR,
    last_name   VARCHAR,
    passport    VARCHAR,
    age         INT,
    phone1      VARCHAR,
    phone2      VARCHAR,
    email       VARCHAR,
    telegram    VARCHAR, -- telegram id
    volunteer_id    INT, -- Lookup to [volunteers] table
    on_probation    BOOL,
    active          BOOL
);

--changeset itamerlan:3
CREATE TABLE IF NOT EXISTS volunteers
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    telegram    VARCHAR,
    picture     BYTEA
);

--changeset itamerlan:4
CREATE TABLE IF NOT EXISTS adoption_reports
(
    id  BIGSERIAL PRIMARY KEY,
    adopter_id  BIGSERIAL,
    report_date TIMESTAMP,
    pet_id      INT,
    picture     BYTEA,
    diet        VARCHAR,
    wellbeing   VARCHAR,
    behavior_change VARCHAR
)

-- changeset alexeym75:1
CREATE TABLE IF NOT EXISTS branch_params
(
    id      INT PRIMARY KEY,
    name    VARCHAR,
    country VARCHAR,
    city    VARCHAR,
    zip     VARCHAR,
    address VARCHAR,
    work_hours  VARCHAR,
    map         BYTEA, -- directions how to get there
    info        TEXT,  -- other information
    prob_period INT,   -- probation period (in days)
    prob_extend INT    -- probation extension period (in days)
);

-- changeset alexeym75:2
CREATE TABLE IF NOT EXISTS pets
(
    id          BIGSERIAL PRIMARY KEY,
    nick_name   VARCHAR,
    pet_type    INT, -- enum PetType
    --breed_id	INT, -- lookup to [breeds] table
    color       INT, -- enum Color
    sex         INT  -- enum Sex
    --picture     BYTEA,
    --adopter_id  INT  -- lookup to [adopters] table
);

-- changeset alexeym75:3
ALTER TABLE volunteers DROP COLUMN telegram;
ALTER TABLE volunteers ADD COLUMN chat_id BIGINT;
ALTER TABLE volunteers ADD COLUMN username VARCHAR;

-- changeset alexeym75:4
CREATE SEQUENCE branch_params_id_seq;
ALTER TABLE branch_params ALTER COLUMN id SET DEFAULT nextval('branch_params_id_seq');

-- changeset alexeym75:5
ALTER TABLE adopters DROP COLUMN on_probation;
ALTER TABLE adopters DROP COLUMN active;
ALTER TABLE adopters ADD COLUMN status INT;

-- changeset alexeym75:6
ALTER TABLE pets ADD COLUMN breed_id INT; -- lookup to [breeds] table
ALTER TABLE pets ADD COLUMN picture BYTEA;
ALTER TABLE pets ADD COLUMN adopter_id INT; -- lookup to [adopters] table

-- changeset alexeym75:7
ALTER TABLE guests DROP COLUMN telegram_id;
ALTER TABLE guests ADD COLUMN chat_id BIGINT;
ALTER TABLE guests ADD COLUMN username VARCHAR;
