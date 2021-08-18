package country.code.persistence.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "iso_codes")
class IsoCode(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    @Column(name = "code") val code: String
)
