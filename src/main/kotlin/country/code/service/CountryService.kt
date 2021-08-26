package country.code.service

import country.code.service.dto.Country

interface CountryService {
    fun getBy(isoCode: String, language: String): Country
}
