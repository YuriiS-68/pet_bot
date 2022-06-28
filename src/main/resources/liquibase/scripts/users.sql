-- liquibase formatted sql

-- changeSet ikalmykov:1
    CREATE TABLE users (
    id SERIAL NOT NULL primary key,
    chat_id INTEGER NOT NULL ,
    name TEXT NOT NULL,
    phone_number INTEGER,
    location TEXT)

-- changeSet ikalmykov:2
    CREATE TABLE pets(
    id SERIAL NOT NULL PRIMARY KEY,
    name TEXT NOT NULL ,
    age INTEGER,
    type TEXT NOT NULL DEFAULT 'DOG',
    CONSTRAINT type_check CHECK (type IN ('CAT','DOG'))
)

-- changeSet ikalmykov:3
    CREATE TABLE volunteers(
    id SERIAL NOT NULL PRIMARY KEY,
    name TEXT NOT NULL ,
    phone_number TEXT NOT NULL
)

-- changeSet ikalmykov:4
    CREATE TABLE reports(
    id SERIAL NOT NULL PRIMARY KEY ,
    chat_id INTEGER,
    report_text TEXT NOT NULL,
    phone_number INTEGER NOT NULL
)

-- changeSet ikalmykov:5
    CREATE TABLE answers (
    id Serial NOT NULL PRIMARY KEY ,
    text_message TEXT NOT NULL
)

-- changeSet ikalmykov:6
    CREATE TABLE pictures(
        id SERIAL PRIMARY KEY,
        file_path TEXT,
        file_size BIGINT,
        media_type TEXT,
        pet_id INTEGER REFERENCES pets(id),
        answer_id INTEGER REFERENCES answers(id)
    )


