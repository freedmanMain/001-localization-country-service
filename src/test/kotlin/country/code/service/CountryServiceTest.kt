package country.code.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.persistence.model.Country
import country.code.persistence.model.IsoCode
import country.code.persistence.model.Language
import country.code.persistence.model.Localization
import country.code.persistence.repository.IsoCodeRepository
import country.code.persistence.repository.LanguageRepository
import country.code.persistence.repository.CountryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CountryServiceTest {
    private var countryRepositoryMock: CountryRepository = mock()
    private var isoCodeRepositoryMockk: IsoCodeRepository = mock()
    private var languageRepositoryMockk: LanguageRepository = mock()
    private val localizationService: CountryService =
        CountryServiceImpl(isoCodeRepositoryMockk, languageRepositoryMockk, countryRepositoryMock)
    private val exceptedCountryId = 1L
    private val exceptedLanguage = Language(1, "EN")
    private val exceptedIsoCodes = listOf(IsoCode(1, "UK"),)
    private val exceptedLocalizations = listOf(Localization(1, "Ukraine", exceptedLanguage))

    @BeforeEach
    fun setUp() {
        reset(countryRepositoryMock, isoCodeRepositoryMockk, languageRepositoryMockk)
    }

    @Test
    fun `given existent data it should provide localization`() {
        whenever(isoCodeRepositoryMockk.existsByIsoCode("UK")).thenReturn(true)
        whenever(languageRepositoryMockk.existsByLanguage("EN")).thenReturn(true)

        whenever(countryRepositoryMock.findCountryByIsoCodeAndLanguage("UK", "EN"))
            .thenReturn(
                Country(
                    exceptedCountryId,
                    exceptedIsoCodes,
                    exceptedLocalizations
                )
            )

        val actual = localizationService.getCountryByIsoCodeAndLanguage("UK", "EN")

        verify(countryRepositoryMock).findCountryByIsoCodeAndLanguage("UK", "EN")

        assertThat(actual).isNotNull
        assertThat(actual).matches { it.id == exceptedCountryId }
        assertThat(actual).matches { it.isoCodes.containsAll(exceptedIsoCodes) }
        assertThat(actual).matches { it.localizations.containsAll(exceptedLocalizations) }
    }

    @Test
    fun `given unknown iso code it should provide exception`() {
        whenever(isoCodeRepositoryMockk.existsByIsoCode("UK"))
            .thenThrow(UnknownIsoCodeApplicationException::class.java)

        assertThrows<UnknownIsoCodeApplicationException> {
            localizationService.getCountryByIsoCodeAndLanguage(
                "UK",
                "EN"
            )
        }
    }

    @Test
    fun `given unknown language it should provide exception`() {
        whenever(isoCodeRepositoryMockk.existsByIsoCode("UK"))
            .thenReturn(true)
        whenever(languageRepositoryMockk.existsByLanguage("JA"))
            .thenThrow(UnknownLanguageApplicationException::class.java)
        assertThrows<UnknownLanguageApplicationException> {
            localizationService.getCountryByIsoCodeAndLanguage(
                "UK",
                "JA"
            )
        }
    }

}
