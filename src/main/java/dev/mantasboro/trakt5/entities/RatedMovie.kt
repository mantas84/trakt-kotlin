package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.OffsetDateTime

class RatedMovie(
    val movie: Movie? = null,
    rated_at: OffsetDateTime?,
    rating: Rating?
) : BaseRatedEntity(rated_at, rating)
