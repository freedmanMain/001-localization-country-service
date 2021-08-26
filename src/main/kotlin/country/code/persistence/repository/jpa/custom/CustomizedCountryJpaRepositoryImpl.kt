package country.code.persistence.repository.jpa.custom

import country.code.persistence.model.CountryEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Repository
class CustomizedCountryJpaRepositoryImpl(
    val em: EntityManager
) : CustomizedCountryJpaRepository {
    @Transactional(readOnly = true)
    override fun findBy(isoCode: String, language: String): CountryEntity? {
        em.createQuery(
            "select c1 from CountryEntity c1 "
                    + "left join fetch c1.isoCodes i1 "
                    + "where i1.isoCode = :isoCode", CountryEntity::class.java
        ).setParameter("isoCode", isoCode).singleResult
        return em.createQuery(
            "select c1 from CountryEntity c1 "
                    + "left join fetch c1.localizations l1 "
                    + "where l1.language.language = :language", CountryEntity::class.java
        ).setParameter("language", language).singleResult
    }
}
