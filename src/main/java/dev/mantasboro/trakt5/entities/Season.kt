package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

data class Season(
    val number: Int? = null,
    val ids: SeasonIds? = null,
    val title: String? = null,
    val overview: String? = null,
    val network: String? = null,
    val first_aired: OffsetDateTime? = null,
    val rating: Double? = null,
    val votes: Int? = null,
    val episode_count: Int? = null,
    val aired_episodes: Int? = null,
    val episodes: List<Episode>? = null,
)
