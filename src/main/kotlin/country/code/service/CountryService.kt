package country.code.service

import country.code.persistence.model.Country

interface CountryService {
    fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): Country
}
