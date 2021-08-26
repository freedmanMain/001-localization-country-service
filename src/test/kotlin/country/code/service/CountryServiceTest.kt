package country.code.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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

    private val countryService: CountryService = CountryServiceImpl(countryRepository, countryCodeRepository, countryLanguageRepository)

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
        whenever(countryCodeRepository.existBy("UK"))
            .thenThrow(UnknownIsoCodeApplicationException::class.java)

        assertThrows<UnknownIsoCodeApplicationException> {
            countryService.getBy(
                "UK",
                "EN"
            )
        }
    }

    @Test
    fun `given unknown language it should provide exception`() {
        whenever(countryCodeRepository.existBy("UK"))
            .thenReturn(true)
        whenever(countryLanguageRepository.existBy("JA"))
            .thenThrow(UnknownLanguageApplicationException::class.java)
        assertThrows<UnknownLanguageApplicationException> {
            countryService.getBy(
                "UK",
                "JA"
            )
        }
    }

}
