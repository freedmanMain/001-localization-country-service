package country.code.persistence.repository.jpa

import country.code.persistence.model.IsoCodeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface IsoCodeJpaRepository : JpaRepository<IsoCodeEntity, Long> {
    fun existsByIsoCode(countryIsoCode: String): Boolean
}
