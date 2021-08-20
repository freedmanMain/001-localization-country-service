package country.code.controller

import country.code.persistence.model.dto.CountryCodeAndLocalizationResponseDto
import country.code.service.CountryService
import country.code.service.mapper.CountryCodeAndLocalizationResponseMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class CountryController(
    val countryService: CountryService,
    val countryCodeAndLocalizationResponseMapper: CountryCodeAndLocalizationResponseMapper
) {
    @GetMapping("{id}")
    fun getLocalizationByIsoCodeAndLanguage(
        @PathVariable("id") isoCode: String,
        @RequestParam language: String
    ): CountryCodeAndLocalizationResponseDto {
        val country = countryService.getCountryByIsoCodeAndLanguage(isoCode, language)
        return countryCodeAndLocalizationResponseMapper.toDto(country, isoCode, language)
    }
}
