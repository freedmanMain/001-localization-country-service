create table localizations
(
    id           bigserial not null,
    localization varchar(255),
    language_id  int8,
    country_id   int8,
    primary key (id)
)
