package country.code.persistence.repository.custom

import country.code.persistence.model.Country

interface CustomizedCountryRepository {
    fun findCountryByIsoCodeAndLanguage(
        isoCode: String,
        language: String
    ): Country?
}
