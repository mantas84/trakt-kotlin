package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.OffsetDateTime

class RatedEpisode(
    val episode: Episode?,
    show: Show?,
    rated_at: OffsetDateTime?,
    rating: Rating?
) : RatedShow(show, rated_at, rating)
