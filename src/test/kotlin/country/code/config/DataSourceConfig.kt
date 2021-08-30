package country.code.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import country.code.testcontainer.postgreSQLContainer
import javax.sql.DataSource

fun getDataSource(): DataSource = HikariDataSource(hikariConfig())

fun hikariConfig(): HikariConfig {
    postgreSQLContainer.start()
    return HikariConfig()
            .apply {
                jdbcUrl = postgreSQLContainer.jdbcUrl
                username = postgreSQLContainer.username
                password = postgreSQLContainer.username
            }
}
