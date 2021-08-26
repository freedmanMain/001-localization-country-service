package country.code.persistence.repository

import country.code.persistence.model.CountryEntity
import country.code.persistence.repository.jpa.CountryJpaRepository
import country.code.service.dto.Code
import country.code.service.dto.Country
import country.code.service.dto.Language
import country.code.service.repository.CountryRepository
import org.springframework.stereotype.Repository

@Repository
class CountryRepositoryImpl(
    val countryJpaRepository: CountryJpaRepository
) : CountryRepository {
    override fun findBy(code: Code, language: Language): Country? =
        countryJpaRepository.findBy(code.value, language.value)
            ?.toCountry()

    fun CountryEntity.toCountry() =
        Country(
            isoCodes.first().isoCode,
            localizations.first().localization
        )
}
