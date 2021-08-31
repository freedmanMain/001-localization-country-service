package country.code.persistence.repository

import country.code.service.repository.CountryCodeRepository
import org.intellij.lang.annotations.Language
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CountryCodeRepositoryImpl(
    private val jdbc: NamedParameterJdbcTemplate
) : CountryCodeRepository {

    private companion object {
        @Language("postgresql")
        private const val SQL = "select exists(select * from iso_codes i1 where i1.iso_code = :iso_code)"
    }

    override fun existBy(value: String) =
        jdbc.queryForObject(SQL, mapOf("iso_code" to value), Boolean::class.java)!!
}
