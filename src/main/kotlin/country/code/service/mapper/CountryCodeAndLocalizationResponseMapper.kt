package country.code.service.mapper

import country.code.persistence.model.Country
import country.code.persistence.model.dto.CountryCodeAndLocalizationResponseDto
import org.springframework.stereotype.Component

@Component
class CountryCodeAndLocalizationResponseMapper {
    fun toDto(country: Country, isoCode: String, language: String): CountryCodeAndLocalizationResponseDto {
        val countryCode = country.isoCodes
            .find { it.isoCode == isoCode }!!.isoCode

        val localization = country.localizations
            .find { it.language.language == language }!!.localization

        return CountryCodeAndLocalizationResponseDto(countryCode, localization)
    }
}
