package country.code.service.dto

import country.code.service.repository.CountryLanguageRepository

@JvmInline
value class Language private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String, repository: CountryLanguageRepository) = of(value, repository)

        private fun of(value: String, repository: CountryLanguageRepository) =
            if (repository.existBy(value)) Language(value) else null
    }
}
