package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

data class Followed(
    val approved_at: OffsetDateTime?,
    val user: User?
)
