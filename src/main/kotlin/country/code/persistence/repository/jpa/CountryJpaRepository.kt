package country.code.persistence.repository.jpa

import country.code.persistence.model.CountryEntity
import country.code.persistence.repository.jpa.custom.CustomizedCountryJpaRepository
import org.springframework.data.jpa.repository.JpaRepository

interface CountryJpaRepository : JpaRepository<CountryEntity, Long>, CustomizedCountryJpaRepository
