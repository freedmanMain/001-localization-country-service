package country.code.service

import country.code.exception.NotFoundCountryLocalizationApplicationException
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.service.dto.Code
import country.code.service.dto.Country
import country.code.service.dto.Language
import country.code.service.repository.CountryRepository
import country.code.service.repository.CountryCodeRepository
import country.code.service.repository.CountryLanguageRepository
import org.springframework.stereotype.Service

@Service
class CountryServiceImpl(
    private val countryRepository: CountryRepository,
    private val countyCodeRepository: CountryCodeRepository,
    private val countryLanguageRepository: CountryLanguageRepository
) : CountryService {
    override fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): Country {
        val isoCodeInUpperCase = isoCode.uppercase()
        val languageInUpperCase = language.uppercase()

        val code = Code(isoCodeInUpperCase, countyCodeRepository)
            ?: throw UnknownIsoCodeApplicationException(
                "Unknown iso code format $isoCode. "
                        + "The code must be in the following formats Alpha-2, Alpha-3 or Numeric"
            )
        val lang = Language(languageInUpperCase, countryLanguageRepository)
            ?: throw UnknownLanguageApplicationException(
                "Unknown language format $language. "
                        + "The format must be in the following format ISO 639"
            )

        return countryRepository.findBy(code = code, language = lang)
            ?: throw NotFoundCountryLocalizationApplicationException("Couldn't find country by this $isoCodeInUpperCase and $languageInUpperCase")
    }
}
