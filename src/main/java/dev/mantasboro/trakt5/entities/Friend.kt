package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime


data class Friend(
    val friends_at: OffsetDateTime?,
    val user: User?
)