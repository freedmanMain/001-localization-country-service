package country.code.persistence.repository

import country.code.persistence.model.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long> {
    @Query(
        "select c1 from Country c1 " +
                "left join fetch c1.isoCodes i1 " +
                "left join fetch c1.localizations l1 " +
                "left join fetch  l1.language l2 " +
                "where i1.isoCode = :isoCode and l2.language = :language"
    )
    fun findCountryByIsoCodeAndLanguage(
        @Param("isoCode") isoCode: String,
        @Param("language") language: String
    ): Country?
}
