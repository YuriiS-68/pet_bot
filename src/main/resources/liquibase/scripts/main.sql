-- liquibase formatted sql

-- changeSet yuvis:1
CREATE TABLE answer (
    id SERIAL NOT NULL PRIMARY KEY,
    chat_id INTEGER NOT NULL,
    id_message INTEGER NOT NULL
);

-- changeSet yuvis:2
CREATE TABLE users (
    id SERIAL NOT NULL PRIMARY KEY,
    chat_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    phone_number VARCHAR(30),
    location TEXT
);

-- changeSet yuvis:3
CREATE TABLE pets (
    id SERIAL NOT NULL PRIMARY KEY,
    pet_name VARCHAR(100) NOT NULL,
    age INTEGER,
    type VARCHAR(50) NOT NULL DEFAULT 'DOG'
                  CONSTRAINT type_check CHECK ( type IN ('DOG', 'CAT'))
);

-- changeSet yuvis:4
CREATE TABLE shelters (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(300) NOT NULL,
    address VARCHAR(500) NOT NULL,
    access_rules TEXT NOT NULL,
    type VARCHAR(50) NOT NULL DEFAULT 'FOR_DOG'
                      CONSTRAINT type_check CHECK ( type IN ('FOR_DOG', 'FOR_CAT'))
);

-- changeSet yuvis:5
CREATE TABLE volunteers (
    id SERIAL NOT NULL PRIMARY KEY,
    chat_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    phone_number VARCHAR(30)
);