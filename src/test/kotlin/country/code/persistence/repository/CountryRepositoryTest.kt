package country.code.persistence.repository

import country.code.testcontainer.postgreSQLContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CountryRepositoryTest(@Autowired private val repository: CountryRepository) {
    companion object {
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
    internal fun `should get country by iso code and language`() {
        val actual = repository.findCountryByIsoCodeAndLanguage("UK", "EN")
        assertThat(actual).isNotNull
        assertThat(actual).matches { it?.id == 2L }
        assertThat(actual).matches { it?.isoCode?.code == "UK" }
        assertThat(actual).matches { it?.language?.language == "EN" }
        assertThat(actual).matches { it?.countryLocalization?.localization == "Ukraine" }
    }
}
