package country.code.controller

import country.code.test.datasource.MockCountryDataSource
import country.code.testcontainer.postgreSQLContainer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
internal class CountryControllerIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
) {
    val apiUrl = "/countries/"
    val exceptedCountryCode = "UK"
    val exceptedCountryLocalization = "Ukraine"

    private companion object {
        @Autowired
        private lateinit var dataSource: MockCountryDataSource

        @JvmStatic
        @DynamicPropertySource
        fun setUpDynamicProperties(registry: DynamicPropertyRegistry) {
            postgreSQLContainer.start()
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
        }
        @BeforeAll
        fun init() {
            dataSource.addMockDataSource()
        }
        @AfterAll
        fun close() {
            dataSource.deleteMockDataSource()
        }
    }

    @Test
    fun `should provide response with OK status and content type json`() {
        mockMvc.perform(get("${apiUrl}UK?language=EN"))
            .andExpect {
                status().isOk
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.countryCode").value(exceptedCountryCode)
                jsonPath("$.countyLocalization").value(exceptedCountryLocalization)
            }
    }

    @Test
    fun `should provide response with BAD_REQUEST http code and error code 10`() {
        mockMvc.perform(get("${apiUrl}PL?language=EN"))
            .andExpect {
                status().isBadRequest
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.error").value("10")
            }
    }

    @Test
    fun `should provide response with BAD_REQUEST http code and error code 20`() {
        mockMvc.perform(get("${apiUrl}UK?language=JA"))
            .andExpect {
                status().isBadRequest
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.error").value("20")
            }
    }

    @Test
    fun `should provide response with NOT_FOUND http code and error code 30`() {
        mockMvc.perform(get("${apiUrl}RU?language=UA"))
            .andExpect {
                status().isNotFound
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.error").value("30")
            }
    }
}
