alter table if exists iso_codes
    add constraint fk_countries_iso_codes
    foreign key (country_id)
    references countries
