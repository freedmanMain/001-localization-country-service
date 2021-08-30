package country.code.persistence.repository

import country.code.service.repository.CountryCodeRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CountryCodeRepositoryImpl(
    private val jdbc: NamedParameterJdbcTemplate
) : CountryCodeRepository {

    override fun existBy(value: String): Boolean {
        val sql = "select exists(select * from iso_codes i1 where i1.iso_code = :iso_code)"
        return jdbc.queryForObject(sql, mapOf("iso_code" to value), Boolean::class.java)!!
    }
}
