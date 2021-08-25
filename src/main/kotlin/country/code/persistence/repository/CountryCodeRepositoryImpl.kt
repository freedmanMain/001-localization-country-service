package country.code.persistence.repository

import country.code.persistence.repository.jpa.IsoCodeJpaRepository
import country.code.service.repository.CountryCodeRepository
import org.springframework.stereotype.Repository

@Repository
class CountryCodeRepositoryImpl(
    val isoCodeJpaRepository: IsoCodeJpaRepository
) : CountryCodeRepository {
    override fun exist(value: String) = isoCodeJpaRepository.existsByIsoCode(value)
}
