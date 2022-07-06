CREATE DATABASE booking_hotel_repository;


CREATE TABLE IF NOT EXISTS room
(
    id           BIGSERIAL PRIMARY KEY,
    numbers_room SMALLINT     NOT NULL UNIQUE,
    floor_hotel  SMALLINT     NOT NULL,
    type_room    VARCHAR(128) NOT NULL,
    sizing_room  VARCHAR(128) NOT NULL,
    price_room   INT          NOT NULL
    DEFAULT 0
    );


CREATE TABLE IF NOT EXISTS hotel
(
    id                  BIGSERIAL PRIMARY KEY,
    name_hotel          VARCHAR(128) NOT NULL UNIQUE,
    addres_city         VARCHAR(128) NOT NULL,
    addres_street       VARCHAR(128) NOT NULL,
    addres_number_house SMALLINT     NOT NULL,
    room_id             INT REFERENCES room (id)
    );


CREATE TABLE IF NOT EXISTS "user"
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    phone      BIGINT       NOT NULL,
    is_root    BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS booking_room
(
    id             BIGSERIAL PRIMARY KEY,
    date_start     DATE NOT NULL,
    date_finish    DATE NOT NULL,
    room_number_id INT  NOT NULL REFERENCES room (numbers_room),
    user_id        INT  NOT NULL REFERENCES "user" (id)

    );