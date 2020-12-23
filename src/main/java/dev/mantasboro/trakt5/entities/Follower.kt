package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

data class Follower(
    val followed_at: OffsetDateTime?,
    val user: User?
)