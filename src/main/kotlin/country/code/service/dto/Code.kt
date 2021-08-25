package country.code.service.dto

import country.code.service.repository.CountryCodeRepository

@JvmInline
value class Code private constructor(val value: String) {

    companion object {

        operator fun invoke(value: String, repository: CountryCodeRepository): Code? = of(value, repository)

        private fun of(value: String, repository: CountryCodeRepository): Code? =
            if (repository.exist(value)) Code(value) else null
    }
}
