package country.code.persistence.repository

import country.code.persistence.model.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long> {
    @Query(
        "from Country lc " +
                "left join fetch lc.isoCode ic " +
                "left join fetch lc.language la " +
                "left join fetch lc.countryLocalization cl " +
                "where ic.code = ?1 and la.language = ?2"
    )
    fun findCountryByIsoCodeAndLanguage(isoCode: String, language: String): Country?
}
