package country.code.persistence.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "languages")
class Language(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    @Column(name = "language") val language: String
)
