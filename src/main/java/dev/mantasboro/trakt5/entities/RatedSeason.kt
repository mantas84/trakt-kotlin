package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.OffsetDateTime

class RatedSeason(
    val season: Season?,
    show: Show? = null,
    rated_at: OffsetDateTime?,
    rating: Rating?
) : RatedShow(show, rated_at, rating)