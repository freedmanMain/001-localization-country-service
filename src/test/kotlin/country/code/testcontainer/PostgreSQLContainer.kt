package country.code.testcontainer

import org.testcontainers.containers.PostgreSQLContainer

val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:9.6-alpine")
    .apply {
        withDatabaseName("countries_db")
        withUsername("postgres")
        withPassword("postgres")
    }
