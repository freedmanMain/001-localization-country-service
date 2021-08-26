package country.code.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import country.code.exception.NotFoundCountryLocalizationApplicationException
import country.code.exception.UnknownIsoCodeApplicationException
import country.code.exception.UnknownLanguageApplicationException
import country.code.service.dto.Code
import country.code.service.dto.Country
import country.code.service.dto.Language
import country.code.service.repository.CountryCodeRepository
import country.code.service.repository.CountryLanguageRepository
import country.code.service.repository.CountryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CountryServiceTest {
    private val countryRepository: CountryRepository = mock()
    private val countryCodeRepository: CountryCodeRepository = mock()
    private val countryLanguageRepository: CountryLanguageRepository = mock()

    private val countryService: CountryService =
        CountryServiceImpl(countryRepository, countryCodeRepository, countryLanguageRepository)

    private val exceptedCountryCode = "UK"
    private val exceptedCountryLocalization = "Ukraine"

    @BeforeEach
    fun setUp() {
        reset(countryRepository, countryCodeRepository, countryLanguageRepository)
    }

    @Test
    fun `given existent data it should provide localization`() {
        whenever(countryCodeRepository.existBy("UK")).thenReturn(true)
        whenever(countryLanguageRepository.existBy("EN")).thenReturn(true)

        val code = Code("UK", countryCodeRepository)
        val lang = Language("EN", countryLanguageRepository)

        whenever(countryRepository.findBy(code!!, lang!!))
            .thenReturn(
                Country(exceptedCountryCode, exceptedCountryLocalization)
            )

        val actual = countryService.getBy("UK", "EN")

        verify(countryRepository).findBy(code, lang)

        assertEquals(exceptedCountryCode, actual.code)
        assertEquals(exceptedCountryLocalization, actual.name)
    }

    @Test
    fun `given unknown iso code it should provide exception`() {
        val message =
            "Unknown iso code format UK. The code must be in the following formats Alpha-2, Alpha-3 or Numeric"

        whenever(countryCodeRepository.existBy("UK"))
            .thenThrow(UnknownIsoCodeApplicationException(message))

        val exception = assertThrows<UnknownIsoCodeApplicationException> {
            countryService.getBy(
                "UK",
                "EN"
            )
        }
        assertEquals(message, exception.message)
        assertEquals(10, exception.errorCode)
    }

    @Test
    fun `given unknown language it should provide exception`() {
        val message = "Unknown language format JA. The format must be in the following format ISO 639"
        whenever(countryCodeRepository.existBy("UK"))
            .thenReturn(true)
        whenever(countryLanguageRepository.existBy("JA"))
            .thenThrow(UnknownLanguageApplicationException(message))
        val exception = assertThrows<UnknownLanguageApplicationException> {
            countryService.getBy(
                "UK",
                "JA"
            )
        }
        assertEquals(message, exception.message)
        assertEquals(20, exception.errorCode)
    }

    @Test
    fun `given existent language and country code but country localization was not founded this case should throw exception`() {
        val message = "Couldn't find country by this UK and EN"
        whenever(countryCodeRepository.existBy("UK"))
            .thenReturn(true)
        whenever(countryLanguageRepository.existBy("EN"))
            .thenReturn(true)
        val code = Code("UK", countryCodeRepository)
        val lang = Language("EN", countryLanguageRepository)
        whenever(
            countryRepository.findBy(
                code!!,
                lang!!
            )
        ).thenThrow(NotFoundCountryLocalizationApplicationException(message))
        val exception = assertThrows<NotFoundCountryLocalizationApplicationException> {
            countryService.getBy("UK", "EN")
        }
        assertEquals(message, exception.message)
        assertEquals(30, exception.errorCode)
    }

}
