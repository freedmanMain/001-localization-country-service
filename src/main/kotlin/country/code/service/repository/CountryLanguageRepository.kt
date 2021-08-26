package country.code.service.repository

interface CountryLanguageRepository {
    fun existBy(language: String): Boolean
}
