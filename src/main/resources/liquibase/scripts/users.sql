-- liquibase formatted sql

-- changeSet ikalmykov:1
    CREATE TABLE users (
    id SERIAL primary key,
    chat_id INTEGER,
    name TEXT,
    phone_number INTEGER,
    location TEXT)

-- changeSet ikalmykov:2
    CREATE TABLE pets(
    id SERIAL primary key,
    pet_name TEXT,
    age INTEGER
)
-- changeSet ikalmykov:3
    CREATE TABLE volunteers(
    id SERIAL primary key,
    volunteer_name TEXT,
    phone_number INTEGER
)

-- changeSet ikalmykov:4
    CREATE TABLE reports(
    id SERIAL primary key,
    chat_id INTEGER,
    report_text TEXT,
    phone_number INTEGER
)

-- changeSet ikalmykov:5
    CREATE TABLE answers (
    id Serial primary key,
    text_message TEXT
)