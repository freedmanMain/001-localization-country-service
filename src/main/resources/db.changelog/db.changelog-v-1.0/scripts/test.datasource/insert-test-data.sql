insert into languages (id, language) values(1, 'EN');
insert into languages (id, language) values(2, 'RU');
insert into languages (id, language) values(3, 'UA');

insert into countries(id) values(1);

insert into iso_codes(id, iso_code, country_id) values(1, 'UK', 1);
insert into iso_codes(id, iso_code, country_id) values(2, 'UKR', 1);
insert into iso_codes(id, iso_code, country_id) values(3, '804', 1);

insert into localizations(id, localization, language_id, country_id) values(1, 'Ukraine', 1, 1);
insert into localizations(id, localization, language_id, country_id) values(2, 'Украина', 2, 1);
insert into localizations(id, localization, language_id, country_id) values(3, 'Україна', 3, 1);
