create table persons
(
    id        serial primary key,
    name      text,
    age       serial,
    isLicense boolean,
    cars_id   serial references cars (id)
);

create table cars
(
    id    serial primary key,
    brand text,
    model text,
    price integer check ( price > 0 )
);