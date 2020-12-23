package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

class CalendarShowEntry(
    var first_aired: OffsetDateTime? = null,
    var episode: Episode? = null,
    var show: Show? = null,
)