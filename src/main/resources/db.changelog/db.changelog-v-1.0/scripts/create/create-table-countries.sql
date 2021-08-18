create table countries
(
    id              bigserial not null,
    country_localization_id int8,
    iso_code_id     int8,
    language_id     int8,
    primary key (id)
)
