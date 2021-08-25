package country.code.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.persistence.model.CountryEntity
import country.code.persistence.model.IsoCodeEntity
import country.code.persistence.model.LanguageEntity
import country.code.persistence.model.LocalizationEntity
import country.code.persistence.repository.CountryRepositoryImpl
import country.code.persistence.repository.jpa.IsoCodeJpaRepository
import country.code.persistence.repository.jpa.LanguageJpaRepository
import country.code.persistence.repository.jpa.CountryJpaRepository
import country.code.service.repository.CountryRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CountryServiceTest {
    private val countryJpaRepositoryMock: CountryJpaRepository = mock()
    private val isoCodeRepositoryMockk: IsoCodeJpaRepository = mock()
    private val languageRepositoryMockk: LanguageJpaRepository = mock()
    private val countryRepository: CountryRepository = CountryRepositoryImpl(countryJpaRepositoryMock)
    private val localizationService: CountryService =
        CountryServiceImpl(isoCodeRepositoryMockk, languageRepositoryMockk, countryRepository)

    private val exceptedCountryEntityId = 1L
    private val exceptedCountryEntityLanguage = LanguageEntity(1, "EN")
    private val exceptedCountryEntityIsoCodes = listOf(IsoCodeEntity(1, "UK"))
    private val exceptedCountryEntityLocalizations = listOf(LocalizationEntity(1, "Ukraine", exceptedCountryEntityLanguage))

    private val exceptedCountryCode = "UK"
    private val exceptedCountryLocalization = "Ukraine"

    @BeforeEach
    fun setUp() {
        reset(countryJpaRepositoryMock, isoCodeRepositoryMockk, languageRepositoryMockk)
    }

    @Test
    fun `given existent data it should provide localization`() {
        whenever(isoCodeRepositoryMockk.existsByIsoCode("UK")).thenReturn(true)
        whenever(languageRepositoryMockk.existsByLanguage("EN")).thenReturn(true)

        whenever(countryJpaRepositoryMock.findCountryByIsoCodeAndLanguage("UK", "EN"))
            .thenReturn(
                CountryEntity(
                    exceptedCountryEntityId,
                    exceptedCountryEntityIsoCodes,
                    exceptedCountryEntityLocalizations
                )
            )

        val actual = localizationService.getCountryByIsoCodeAndLanguage("UK", "EN")

        verify(countryJpaRepositoryMock).findCountryByIsoCodeAndLanguage("UK", "EN")

        assertThat(actual).isNotNull
        assertThat(actual).matches { it.code == exceptedCountryCode }
        assertThat(actual).matches { it.name ==  exceptedCountryLocalization}
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
