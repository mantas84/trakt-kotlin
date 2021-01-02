package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDate

@JsonClass(generateAdapter = true)
data class Person(
    var name: String? = null,
    var ids: PersonIds? = null,

    // extended info
    var biography: String? = null,
    var birthday: LocalDate? = null,
    var death: LocalDate? = null,
    var birthplace: String? = null,
    var homepage: String? = null,
)
