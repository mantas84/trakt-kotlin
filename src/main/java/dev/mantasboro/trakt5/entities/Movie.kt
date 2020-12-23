package dev.mantasboro.trakt5.entities

import org.threeten.bp.LocalDate

class Movie : BaseEntity() {
    val year: Int? = null
    val ids: MovieIds? = null

    // extended info
    val certification: String? = null
    val tagline: String? = null

    /** Date in UTC time.  */
    val released: LocalDate? = null
    val runtime: Int? = null
    val trailer: String? = null
    val homepage: String? = null
    val language: String? = null
    val genres: List<String>? = null
}