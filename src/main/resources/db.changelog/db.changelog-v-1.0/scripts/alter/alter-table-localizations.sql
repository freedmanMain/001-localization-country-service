alter table if exists localizations
    add constraint fk_languages_localizations
    foreign key (language_id)
    references languages;

alter table if exists localizations
    add constraint fk_countries_localizations
    foreign key (country_id)
    references countries
