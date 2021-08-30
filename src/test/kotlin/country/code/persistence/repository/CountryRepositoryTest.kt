package country.code.persistence.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import country.code.config.getDataSource
import country.code.service.dto.Code
import country.code.service.dto.Language
import country.code.service.repository.CountryCodeRepository
import country.code.service.repository.CountryLanguageRepository
import country.code.service.repository.CountryRepository
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

internal class CountryRepositoryTest {
    private val countryCodeRepository: CountryCodeRepository = mock()
    private val countryLanguageRepository: CountryLanguageRepository = mock()

    private val exceptedCountryCode = "UK"
    private val exceptedCountryLocalization = "Ukraine"

    companion object {
        private val dataSource = getDataSource()
        private val jdbc = NamedParameterJdbcTemplate(dataSource)
        private val countryRepository: CountryRepository = CountryRepositoryImpl(jdbc)

        @JvmStatic
        @BeforeAll
        fun setUp() {
            val db = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(JdbcConnection(getDataSource().connection))
            val lb = Liquibase("db.changelog/db.changelog-master.xml", ClassLoaderResourceAccessor(), db)
            lb.update(Contexts("test"))
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
    internal fun `should not find country when country code not found`() {
        whenever(countryCodeRepository.existBy("RU")).thenReturn(true)
        whenever(countryLanguageRepository.existBy("EN")).thenReturn(true)

        val code = Code("RU", countryCodeRepository)
        val lang = Language("EN", countryLanguageRepository)

        val actual = countryRepository.findBy(code!!, lang!!)
        assertNull(actual)
    }

    @Test
    internal fun `should not find country when localization language not found`() {
        whenever(countryCodeRepository.existBy("UK")).thenReturn(true)
        whenever(countryLanguageRepository.existBy("UA")).thenReturn(true)

        val code = Code("UK", countryCodeRepository)
        val lang = Language("UA", countryLanguageRepository)

        val actual = countryRepository.findBy(code!!, lang!!)
        assertNull(actual)
    }
}
