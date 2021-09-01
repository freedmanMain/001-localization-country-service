package country.code.persistence.repository

import country.code.service.dto.Code
import country.code.service.dto.Country
import country.code.service.dto.Language
import country.code.service.repository.CountryRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class CountryRepositoryImpl(
    private val jdbc: NamedParameterJdbcTemplate
) : CountryRepository {

    private companion object {
        @org.intellij.lang.annotations.Language("postgresql")
        private const val SQL = "select i1.iso_code as code, l1.localization as country_name " +
                "from countries c1 " +
                "join iso_codes i1 on i1.country_id = c1.id " +
                "join localizations l1 on l1.country_id = c1.id " +
                "join languages l2 on l2.id = l1.language_id " +
                "where i1.iso_code = :code and l2.language = :language"
    }

    override fun findBy(code: Code, language: Language): Country? = try {
        val parameters = mapOf("code" to code.value, "language" to language.value)
        jdbc.queryForObject(SQL, parameters, toCountry())
    } catch (e: EmptyResultDataAccessException) {
        null
    }

    private fun toCountry() = RowMapper<Country> { resultSet, _ ->
        Country(
            resultSet.getString("code"),
            resultSet.getString("country_name")
        )
    }
}
