package country.code.controller

import country.code.service.CountryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class CountryController(
    val countryService: CountryService,
) {
    @GetMapping("{id}")
    fun getLocalizationByIsoCodeAndLanguage(
        @PathVariable("id") isoCode: String,
        @RequestParam language: String
    ) = countryService.getCountryByIsoCodeAndLanguage(isoCode, language)
}
