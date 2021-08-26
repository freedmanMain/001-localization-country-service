package country.code.service

import country.code.exception.NotFoundCountryLocalizationApplicationException
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.service.dto.Code
import country.code.service.dto.Country
import country.code.service.dto.Language
import country.code.service.repository.CountryCodeRepository
import country.code.service.repository.CountryLanguageRepository
import country.code.service.repository.CountryRepository
import org.springframework.stereotype.Service

@Service
class CountryServiceImpl(
    private val countryRepository: CountryRepository,
    private val countyCodeRepository: CountryCodeRepository,
    private val countryLanguageRepository: CountryLanguageRepository
) : CountryService {
    override fun getBy(isoCode: String, language: String): Country {
        val code = Code(isoCode.uppercase(), countyCodeRepository)
            ?: throw UnknownIsoCodeApplicationException(
                "Unknown iso code format $isoCode. "
                        + "The code must be in the following formats Alpha-2, Alpha-3 or Numeric"
            )
        val lang = Language(language.uppercase(), countryLanguageRepository)
            ?: throw UnknownLanguageApplicationException(
                "Unknown language format $language. "
                        + "The format must be in the following format ISO 639"
            )

        return countryRepository.findBy(code = code, language = lang)
            ?: throw NotFoundCountryLocalizationApplicationException(
                "Couldn't find country by this $isoCode and $language"
            )
    }
}
