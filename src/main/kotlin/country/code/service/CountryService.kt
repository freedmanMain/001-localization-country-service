package country.code.service

import country.code.service.dto.Country

interface CountryService {
    fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): Country
}
