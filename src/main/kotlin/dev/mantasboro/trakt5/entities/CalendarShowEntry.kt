package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
class CalendarShowEntry(
    var first_aired: OffsetDateTime? = null,
    var episode: Episode? = null,
    var show: Show? = null,
)
