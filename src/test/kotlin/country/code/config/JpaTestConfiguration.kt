package country.code.config

import country.code.persistence.repository.CountryRepositoryImpl
import country.code.persistence.repository.jpa.CountryJpaRepository
import country.code.service.repository.CountryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JpaTestConfiguration(val countryJpaRepository: CountryJpaRepository) {
    @Bean
    fun countryRepository(): CountryRepository = CountryRepositoryImpl(countryJpaRepository)
}
