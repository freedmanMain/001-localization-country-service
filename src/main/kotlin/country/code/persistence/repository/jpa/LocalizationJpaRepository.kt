package country.code.persistence.repository.jpa

import country.code.persistence.model.LocalizationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LocalizationJpaRepository : JpaRepository<LocalizationEntity, Long>
