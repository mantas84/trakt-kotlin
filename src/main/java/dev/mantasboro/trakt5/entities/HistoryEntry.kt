package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

data class HistoryEntry(
    val id: Long? = null,
    val watched_at: OffsetDateTime? = null,
    val action: String? = null,
    val type: String? = null,
    val episode: Episode? = null,
    val show: Show? = null,
    val movie: Movie? = null,
)