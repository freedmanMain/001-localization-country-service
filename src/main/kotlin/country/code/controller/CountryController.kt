package country.code.controller

import country.code.persistence.model.Country
import country.code.persistence.model.dto.CountryResponseDto
import country.code.service.CountryService
import country.code.service.mapper.ResponseMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class CountryController(
    val countryService: CountryService,
    val responseMapper: ResponseMapper<Country, CountryResponseDto>
) {
    @GetMapping("{id}")
    fun getLocalizationByIsoCodeAndLanguage(
        @PathVariable("id") isoCode: String,
        @RequestParam language: String
    ) = responseMapper.toDto(countryService.getCountryByIsoCodeAndLanguage(isoCode, language))
}
