package country.code.persistence.repository

import country.code.persistence.model.Country
import country.code.persistence.repository.custom.CustomizedCountryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, Long>, CustomizedCountryRepository
