package country.code.persistence.repository

import country.code.persistence.model.IsoCode
import country.code.persistence.model.Language
import country.code.persistence.model.Localization
import country.code.test.datasource.MockCountryDataSource
import country.code.testcontainer.postgreSQLContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
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
@Import(MockCountryDataSource::class)
internal class CountryRepositoryTest @Autowired constructor(
    private val repository: CountryRepository,
    private val datasource: MockCountryDataSource
) {
    private companion object {
        const val exceptedCountryId: Long = 1

        val exceptedIsoCodes = setOf(
            IsoCode(1, "UK"),
            IsoCode(2, "UKR")
        )

        val exceptedLocalizations = setOf(
            Localization(1, "Ukraine", Language(1, "EN")),
            Localization(2, "Украина", Language(2, "RU")),
            Localization(3, "Україна", Language(3, "UA")),
        )

        @JvmStatic
        @DynamicPropertySource
        fun setUpDynamicProperties(registry: DynamicPropertyRegistry) {
            postgreSQLContainer.start()
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
        }
    }

    @BeforeEach
    fun init() {
        datasource.addMockDataSource()
    }

    @AfterEach
    fun close() {
        datasource.deleteMockDataSource()
    }

    @Test
    internal fun `should get country by iso code and language`() {
        val actual = repository.findCountryByIsoCodeAndLanguage("UK", "EN")
        assertThat(actual).isNotNull
        assertThat(actual).matches { it?.id == exceptedCountryId }
        assertThat(actual).matches { it?.isoCodes?.containsAll(exceptedIsoCodes)!! }
        assertThat(actual).matches { it?.localizations?.containsAll(exceptedLocalizations)!! }
    }
}
