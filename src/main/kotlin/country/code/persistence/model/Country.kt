package country.code.persistence.model

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "countries")
class Country(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "country_localization_id") val countryLocalization: Localization,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "iso_code_id") val isoCode: IsoCode,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "language_id") val language: Language
)
