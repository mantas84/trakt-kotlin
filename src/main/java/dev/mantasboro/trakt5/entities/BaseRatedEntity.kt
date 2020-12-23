package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.OffsetDateTime

abstract class BaseRatedEntity(
    val rated_at: OffsetDateTime?,
    val rating: Rating?
)
