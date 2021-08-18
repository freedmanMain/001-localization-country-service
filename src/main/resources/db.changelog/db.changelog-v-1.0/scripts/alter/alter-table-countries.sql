alter table if exists countries
    add constraint fk_iso_codes_localizations
    foreign key (iso_code_id)
    references iso_codes(id);

alter table if exists countries
    add constraint fk_country_names_localizations
    foreign key (country_localization_id)
    references localizations(id);

alter table if exists countries
    add constraint fk_languages_localizations
    foreign key (language_id)
    references languages(id);
