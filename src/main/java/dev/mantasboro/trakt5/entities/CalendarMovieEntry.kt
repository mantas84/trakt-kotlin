package dev.mantasboro.trakt5.entities

import org.threeten.bp.LocalDate

data class CalendarMovieEntry(
    /** Date in UTC time.  */
    val released: LocalDate? = null,
    val movie: Movie? = null,
)