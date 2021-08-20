package country.code.service

import country.code.exception.NotFoundCountryLocalizationApplicationException
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.persistence.model.Country
import country.code.persistence.repository.CountryRepository
import country.code.persistence.repository.IsoCodeRepository
import country.code.persistence.repository.LanguageRepository
import org.springframework.stereotype.Service

@Service
class CountryServiceImpl(
    private val isoCodeRepository: IsoCodeRepository,
    private val languageRepository: LanguageRepository,
    private val countryRepository: CountryRepository
) : CountryService {
    override fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): Country {
        val isoCodeInUpperCase = isoCode.uppercase()
        val languageInUpperCase = language.uppercase()

        if (!isoCodeRepository.existsByIsoCode(isoCodeInUpperCase)) {
            throw UnknownIsoCodeApplicationException(
                "Unknown iso code format $isoCode. "
                        + "The code must be in the following formats Alpha-2, Alpha-3 or Numeric"
            )
        }
        if (!languageRepository.existsByLanguage(languageInUpperCase)) {
            throw UnknownLanguageApplicationException(
                "Unknown language format $language. "
                        + "The format must be in the following format ISO 639"
            )
        }
        return countryRepository.findCountryByIsoCodeAndLanguage(isoCodeInUpperCase, languageInUpperCase)
            ?: throw NotFoundCountryLocalizationApplicationException("Couldn't find country by this $isoCode and $language")
    }
}
