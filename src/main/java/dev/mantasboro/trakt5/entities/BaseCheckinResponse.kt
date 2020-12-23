package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

abstract class BaseCheckinResponse(
    val watched_at: OffsetDateTime? = null,
    val sharing: ShareSettings? = null,
)
