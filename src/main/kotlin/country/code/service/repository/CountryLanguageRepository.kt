package country.code.service.repository

interface CountryLanguageRepository {
    fun exist(language: String): Boolean
}
