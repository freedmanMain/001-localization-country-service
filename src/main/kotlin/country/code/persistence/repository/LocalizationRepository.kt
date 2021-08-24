package country.code.persistence.repository

import country.code.persistence.model.Localization
import org.springframework.data.jpa.repository.JpaRepository

interface LocalizationRepository : JpaRepository<Localization, Long>
