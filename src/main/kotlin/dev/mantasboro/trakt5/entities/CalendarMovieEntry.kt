package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDate

@JsonClass(generateAdapter = true)
data class CalendarMovieEntry(
    /** Date in UTC time.  */
    val released: LocalDate? = null,
    val movie: Movie? = null,
)
