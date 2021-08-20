package country.code.test.datasource

import country.code.persistence.model.Country
import country.code.persistence.model.IsoCode
import country.code.persistence.model.Language
import country.code.persistence.model.Localization
import country.code.persistence.repository.CountryRepository
import country.code.persistence.repository.IsoCodeRepository
import country.code.persistence.repository.LanguageRepository
import country.code.persistence.repository.LocalizationRepository
import org.springframework.stereotype.Component
import org.springframework.test.context.ContextConfiguration

@Component
@ContextConfiguration(
    classes = [
        LanguageRepository::class,
        LocalizationRepository::class,
        IsoCodeRepository::class,
        CountryRepository::class]
)
class MockCountryDataSource(
    val languageRepository: LanguageRepository,
    val localizationRepository: LocalizationRepository,
    val isoCodeRepository: IsoCodeRepository,
    val countryRepository: CountryRepository
) {
    fun addMockDataSource() {
        val en = Language(language = "EN")
        val ru = Language(language = "RU")
        val ua = Language(language = "UA")

        languageRepository.saveAll(listOf(en, ru, ua))

        val ukraineEn = Localization(localization = "Ukraine", language = en)
        val ukraineRu = Localization(localization = "Украина", language = ru)
        val ukraineUa = Localization(localization = "Україна", language = ua)

        val russiaEn = Localization(localization = "Russia", language = en)
        val russiaRu = Localization(localization = "Росия", language = ru)
        val russiaUa = Localization(localization = "Росія", language = ua)

        localizationRepository.saveAll(
            listOf(
                ukraineEn, ukraineRu, ukraineUa,
                russiaEn, russiaRu, russiaUa
            )
        )

        val ukCode = IsoCode(isoCode = "UK")
        val ukrCode = IsoCode(isoCode = "UKR")
        val ruCode = IsoCode(isoCode = "RU")
        val rusCode = IsoCode(isoCode = "RUS")

        isoCodeRepository.saveAll(listOf(ukCode, ukrCode, ruCode, rusCode))

        val ukraine = Country(isoCodes = setOf(ukCode, ukrCode), localizations = setOf(ukraineEn, ukraineRu, ukraineUa))
        val russia = Country(isoCodes = setOf(ruCode, rusCode), localizations = setOf(russiaEn, russiaRu, russiaUa))

        countryRepository.saveAll(listOf(ukraine, russia))
    }

    fun deleteMockDataSource() {
        countryRepository.deleteAll()
        isoCodeRepository.deleteAll()
        localizationRepository.deleteAll()
        languageRepository.deleteAll()
    }
}
