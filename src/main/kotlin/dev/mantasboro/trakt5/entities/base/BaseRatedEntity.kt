package dev.mantasboro.trakt5.entities.base

import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.OffsetDateTime

interface BaseRatedEntity {
    var rated_at: OffsetDateTime?
    var rating: Rating?
}

data class BaseRatedEntityData(
    override var rated_at: OffsetDateTime? = null,
    override var rating: Rating? = null,
) : BaseRatedEntity
