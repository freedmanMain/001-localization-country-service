package country.code.persistence.repository.jpa.custom

import country.code.persistence.model.CountryEntity

interface CustomizedCountryJpaRepository {
    fun findBy(isoCode: String, language: String): CountryEntity?
}
