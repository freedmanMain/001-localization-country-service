package country.code.controller

import country.code.testcontainer.postgreSQLContainer
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
    private companion object {
        private const val API_URL = "/countries/"
        private const val EXCEPTED_COUNTRY_CODE = "UK"
        private const val EXCEPTED_COUNTRY_NAME = "Ukraine"

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
    internal fun `should provide response with OK status and content type json`() {
        mockMvc.perform(get("${API_URL}UK?language=EN"))
            .andExpect {
                status().isOk
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.countryCode").value(EXCEPTED_COUNTRY_CODE)
                jsonPath("$.countyLocalization").value(EXCEPTED_COUNTRY_NAME)
            }
    }

    @Test
    internal fun `should provide response with BAD_REQUEST http code and error code 10`() {
        mockMvc.perform(get("${API_URL}PL?language=EN"))
            .andExpect {
                status().isBadRequest
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.error").value("10")
            }
    }

    @Test
    internal fun `should provide response with BAD_REQUEST http code and error code 20`() {
        mockMvc.perform(get("${API_URL}UK?language=JA"))
            .andExpect {
                status().isBadRequest
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.error").value("20")
            }
    }

    @Test
    internal fun `should provide response with NOT_FOUND http code and error code 30`() {
        mockMvc.perform(get("${API_URL}RU?language=UA"))
            .andExpect {
                status().isNotFound
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.error").value("30")
            }
    }
}
