package country.code.persistence.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "countries")
data class CountryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToMany @JoinColumn(name = "country_id")
    val isoCodes: List<IsoCodeEntity>,

    @OneToMany @JoinColumn(name = "country_id")
    val localizations: List<LocalizationEntity>
)
