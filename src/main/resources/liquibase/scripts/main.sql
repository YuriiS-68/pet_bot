-- liquibase formatted sql

-- changeSet yuvis:1
CREATE TABLE users (
    id SERIAL NOT NULL PRIMARY KEY,
    chat_id INTEGER NOT NULL,
    message_id INTEGER,
    name TEXT NOT NULL,
    first_name VARCHAR(200),
    last_name VARCHAR(200),
    phone_number VARCHAR(30),
    email VARCHAR(200),
    location TEXT,
    start_trial_period TIMESTAMP,
    end_trial_period TIMESTAMP
);

-- changeSet yuvis:2
CREATE TABLE pets (
    id SERIAL NOT NULL PRIMARY KEY,
    pet_name VARCHAR(100) NOT NULL,
    age INTEGER,
    type VARCHAR(50) NOT NULL DEFAULT 'DOG'
                  CONSTRAINT type_check CHECK ( type IN ('DOG', 'CAT'))
);

-- changeSet yuvis:3
CREATE TABLE shelters (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(300) NOT NULL,
    address VARCHAR(500) NOT NULL,
    access_rules TEXT NOT NULL,
    type VARCHAR(50) NOT NULL DEFAULT 'FOR_DOG'
                      CONSTRAINT type_check CHECK ( type IN ('FOR_DOG', 'FOR_CAT'))
);

-- changeSet yuvis:4
CREATE TABLE volunteers (
    id SERIAL NOT NULL PRIMARY KEY,
    chat_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    phone_number VARCHAR(30),
    status VARCHAR(50) DEFAULT 'FREE'
        CONSTRAINT status_check CHECK ( status IN ('FREE', 'BUSY'))
);

-- changeSet yuvis:5
CREATE TABLE reports (
    id SERIAL NOT NULL PRIMARY KEY,
    report_text TEXT NULL,
    time_sending_report TIMESTAMP NULL,
    is_viewed BOOLEAN NOT NULL DEFAULT FALSE
);

-- changeSet yuvis:6
CREATE TABLE pictures (
    id BIGINT,
    file_path TEXT NULL,
    file_size INTEGER NULL,
    media_type VARCHAR(255)
);

-- changeSet yuvis:7
ALTER TABLE pictures ADD COLUMN data_picture bytea;

-- changeSet yuvis:8
ALTER TABLE pictures DROP COLUMN data_picture RESTRICT ;

-- changeSet yuvis:9
ALTER TABLE pictures ADD CONSTRAINT fk_reports FOREIGN KEY (report_id)
    REFERENCES reports (id);
