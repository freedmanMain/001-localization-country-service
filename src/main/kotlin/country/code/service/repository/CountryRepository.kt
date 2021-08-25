package country.code.service.repository

import country.code.service.dto.Code
import country.code.service.dto.Country
import country.code.service.dto.Language

interface CountryRepository {
    fun findBy(code: Code, language: Language): Country?
}
