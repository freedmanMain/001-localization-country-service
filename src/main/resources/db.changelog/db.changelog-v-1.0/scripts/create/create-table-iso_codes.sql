create table iso_codes
(
    id         bigserial not null,
    iso_code   varchar(255),
    country_id int8,
    primary key (id)
)
