package country.code.persistence.model

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "countries")
data class Country(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToMany @JoinColumn(name = "country_id")
    val isoCodes: List<IsoCode>,
    @OneToMany @JoinColumn(name = "country_id")
    val localizations: List<Localization>
)
