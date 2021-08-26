package country.code.persistence.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import country.code.config.JpaTestConfiguration
import country.code.service.dto.Code
import country.code.service.dto.Language
import country.code.service.repository.CountryCodeRepository
import country.code.service.repository.CountryLanguageRepository
import country.code.service.repository.CountryRepository
import country.code.testcontainer.postgreSQLContainer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(JpaTestConfiguration::class)
internal class CountryRepositoryTest @Autowired constructor(
    private val countryRepository: CountryRepository
) {
    private val countryCodeRepository: CountryCodeRepository = mock()
    private val countryLanguageRepository: CountryLanguageRepository = mock()

    private val exceptedCountryCode = "UK"
    private val exceptedCountryLocalization = "Ukraine"

    private companion object {
        @JvmStatic
        @DynamicPropertySource
        fun setUpDynamicProperties(registry: DynamicPropertyRegistry) {
            postgreSQLContainer.start()
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
        }
    }

    @Test
    internal fun `should find country by iso code and language`() {
        whenever(countryCodeRepository.existBy("UK")).thenReturn(true)
        whenever(countryLanguageRepository.existBy("EN")).thenReturn(true)

        val code = Code("UK", countryCodeRepository)
        val lang = Language("EN", countryLanguageRepository)
        val actual = countryRepository.findBy(code!!, lang!!)

        assertEquals(exceptedCountryCode, actual?.code)
        assertEquals(exceptedCountryLocalization, actual?.name)
    }

    @Test
    internal fun `should not find country when localization language not exist`() {
        whenever(countryCodeRepository.existBy("UK")).thenReturn(true)
        whenever(countryLanguageRepository.existBy("UA")).thenReturn(true)

        val code = Code("UK", countryCodeRepository)
        val lang = Language("UA", countryLanguageRepository)

        val actual = countryRepository.findBy(code!!, lang!!)
        assertNull(actual)
    }
}
