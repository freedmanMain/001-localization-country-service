package country.code.service.mapper

import country.code.persistence.model.Country
import country.code.persistence.model.dto.CountryResponseDto
import org.springframework.stereotype.Component

interface ResponseMapper<M, D> {
    fun toDto(model: M): D
}

@Component
class CountryResponseMapper : ResponseMapper<Country, CountryResponseDto> {
    override fun toDto(model: Country): CountryResponseDto =
        CountryResponseDto(model.isoCode.code, model.countryLocalization.localization)
}
