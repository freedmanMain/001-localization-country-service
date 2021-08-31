package country.code.persistence.repository

import country.code.config.getDataSource
import country.code.service.repository.CountryCodeRepository
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

internal class CountryCodeRepositoryTest {

    private companion object {
        private val dataSource = getDataSource()
        private val jdbc = NamedParameterJdbcTemplate(dataSource)
        private val countryCodeRepository: CountryCodeRepository = CountryCodeRepositoryImpl(jdbc)

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
    internal fun `should find existent iso code`() {
        val isExist = countryCodeRepository.existBy("UK")
        assertTrue(isExist)
    }

    @Test
    internal fun `should not find iso code because it doesn't exist`() {
        val isExist = countryCodeRepository.existBy("USA")
        assertFalse(isExist)
    }
}
