package country.code.persistence.repository

import country.code.persistence.model.IsoCode
import org.springframework.data.jpa.repository.JpaRepository

interface IsoCodeRepository : JpaRepository<IsoCode, Long> {
    fun existsByIsoCode(countryIsoCode: String): Boolean
}
