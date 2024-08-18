CREATE TABLE driver (
    id INTEGER PRIMARY KEY,
    firstname VARCHAR,
    lastname VARCHAR,
    age INTEGER,
    has_license BOOLEAN,
    car_id INTEGER REFERENCES car (id)
);

CREATE TABLE car (
    id INTEGER PRIMARY KEY,
    brand VARCHAR,
    model VARCHAR,
    cost BIGINT
)