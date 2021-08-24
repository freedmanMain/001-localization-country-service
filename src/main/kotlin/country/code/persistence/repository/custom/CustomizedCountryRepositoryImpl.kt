package country.code.persistence.repository.custom

import country.code.persistence.model.Country
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
class CustomizedCountryRepositoryImpl(
    val em: EntityManager
) : CustomizedCountryRepository {
    @Transactional
    override fun findCountryByIsoCodeAndLanguage(isoCode: String, language: String): Country? {
        em.createQuery(
            "select c1 from Country c1 "
                    + "left join fetch c1.isoCodes i1 "
                    + "where i1.isoCode = :isoCode", Country::class.java
        ).setParameter("isoCode", isoCode).singleResult
        return em.createQuery(
            "select c1 from Country c1 "
                    + "left join fetch c1.localizations l1 "
                    + "where l1.language.language = :language", Country::class.java
        ).setParameter("language", language).singleResult
    }
}
