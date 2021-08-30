package country.code.persistence.repository

import country.code.config.getDataSource
import country.code.service.repository.CountryLanguageRepository
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

internal class CountryLanguageRepositoryTest {

    companion object {
        private val dataSource = getDataSource()
        private val jdbc = NamedParameterJdbcTemplate(dataSource)
        private val countryLanguageRepository: CountryLanguageRepository = CountryLanguageRepositoryImpl(jdbc)

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
    fun `should find existent language`() {
        val isExist = countryLanguageRepository.existBy("EN")
        Assertions.assertTrue(isExist)
    }

    @Test
    fun `should not find language because it doesn't exist`() {
        val isExist =countryLanguageRepository.existBy("JA")
        Assertions.assertFalse(isExist)
    }
}
