package country.code.persistence.repository

import country.code.persistence.repository.jpa.LanguageJpaRepository
import country.code.service.repository.CountryLanguageRepository
import org.springframework.stereotype.Repository

@Repository
class CountryLanguageRepositoryImpl(
    val languageJpaRepository: LanguageJpaRepository
) : CountryLanguageRepository {
    override fun existBy(language: String) = languageJpaRepository.existsByLanguage(language)
}
