package country.code.service.repository

interface CountryCodeRepository {
    fun exist(value: String): Boolean
}