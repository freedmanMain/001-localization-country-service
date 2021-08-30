package country.code.persistence.repository

import country.code.service.repository.CountryLanguageRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CountryLanguageRepositoryImpl(
    private val jdbc: NamedParameterJdbcTemplate
) : CountryLanguageRepository {

    override fun existBy(language: String): Boolean {
        val sql = "select exists(select * from languages l1 where l1.language = :language)"
        return jdbc.queryForObject(sql, mapOf("language" to language), Boolean::class.java)!!
    }
}