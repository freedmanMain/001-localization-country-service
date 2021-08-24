package country.code.service.mapper

import country.code.persistence.model.Country
import country.code.persistence.model.IsoCode
import country.code.persistence.model.Language
import country.code.persistence.model.Localization
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CountryCodeAndLocalizationResponseMapperTest {
    private val countryCodeAndLocalizationResponseMapper = CountryCodeAndLocalizationResponseMapper()
    private val exceptedCountryCode = "UK"
    private val exceptedLocalization = "Ukraine"

    @Test
    fun `should provide country code and localization response dto`() {
        val id = 1L
        val language = Language(1, "EN")
        val isoCodes = listOf(IsoCode(1, "UK"))
        val localizations = listOf(Localization(1, "Ukraine", language))
        val country = Country(id, isoCodes, localizations)

        val dto = countryCodeAndLocalizationResponseMapper.toDto(country, "UK", "EN")
        assertThat(dto).isNotNull
        assertThat(dto).matches { it.countryCode == exceptedCountryCode }
        assertThat(dto).matches { it.countyLocalization == exceptedLocalization }
    }
}
