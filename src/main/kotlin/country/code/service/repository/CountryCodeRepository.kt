package country.code.service.repository

interface CountryCodeRepository {
    fun existBy(value: String): Boolean
}
