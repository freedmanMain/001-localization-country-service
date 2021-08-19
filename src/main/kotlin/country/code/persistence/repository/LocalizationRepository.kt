package country.code.persistence.repository

import country.code.persistence.model.Localization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalizationRepository : JpaRepository<Localization, Long>
