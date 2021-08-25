package country.code.persistence.repository.jpa

import country.code.persistence.model.LanguageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LanguageJpaRepository : JpaRepository<LanguageEntity, Long> {
    fun existsByLanguage(languageName: String): Boolean
}
