package country.code.persistence.repository

import country.code.service.repository.CountryLanguageRepository
import org.intellij.lang.annotations.Language
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CountryLanguageRepositoryImpl(
    private val jdbc: NamedParameterJdbcTemplate
) : CountryLanguageRepository {

    private companion object {
        @Language("postgresql")
        private const val SQL = "select exists(select * from languages l1 where l1.language = :language)"
    }

    override fun existBy(language: String) =
        jdbc.queryForObject(SQL, mapOf("language" to language), Boolean::class.java)!!
}
