package country.code.test.datasource

import country.code.persistence.model.Country
import country.code.persistence.model.IsoCode
import country.code.persistence.model.Language
import country.code.persistence.model.Localization
import country.code.persistence.repository.CountryRepository
import country.code.persistence.repository.IsoCodeRepository
import country.code.persistence.repository.LanguageRepository
import country.code.persistence.repository.LocalizationRepository
import org.springframework.beans.factory.annotation.Autowired
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
class MockCountryDataSource (
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

//        val ukraineEn = Localization(localization = "Ukraine")
//        val ukraineRu = Localization(localization = "Украина")
//        val ukraineUa = Localization(localization = "Україна")
//
//        val russiaEn = Localization(localization = "Russia")
//        val russiaRu = Localization(localization = "Росия")
//        val russiaUa = Localization(localization = "Росія")
//
//        localizationRepository.saveAll(
//            listOf(
//                ukraineEn, ukraineRu, ukraineUa,
//                russiaEn, russiaRu, russiaUa
//            )
//        )
//
//        val ukCode = IsoCode(code = "UK")
//        val ukrCode = IsoCode(code = "UKR")
//        val ruCode = IsoCode(code = "RU")
//        val rusCode = IsoCode(code = "RUS")
//
//        isoCodeRepository.saveAll(listOf(ukCode, ukrCode, ruCode, rusCode))
//
//        val ukraineEnUk = Country(countryLocalization = ukraineEn, isoCode = ukCode, language = en)
//        val ukraineRuUk = Country(countryLocalization = ukraineRu, isoCode = ukCode, language = ru)
//        val ukraineUaUkr = Country(countryLocalization = ukraineUa, isoCode = ukrCode, language = ua)
//
//        val russiaEnRu = Country(countryLocalization = russiaEn, isoCode = ruCode, language = en)
//        val russiaRuRu = Country(countryLocalization = russiaRu, isoCode = ruCode, language = ru)
//        val russiaUkRus = Country(countryLocalization = russiaUa, isoCode = rusCode, language = ua)
//
//        countryRepository.saveAll(
//            listOf(
//                ukraineEnUk, ukraineRuUk, ukraineUaUkr,
//                russiaEnRu, russiaRuRu, russiaUkRus
//            )
//        )
    }

    fun deleteMockDataSource() {
        countryRepository.deleteAll()
        isoCodeRepository.deleteAll()
        localizationRepository.deleteAll()
        languageRepository.deleteAll()
    }
}
