package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

data class BaseShow(
    val show: Show? = null,

    /** collection, watched  */
    val seasons: List<BaseSeason>? = null,

    /** collection  */
    val last_collected_at: OffsetDateTime? = null,

    /** watchlist  */
    val listed_at: OffsetDateTime? = null,

    /** watched  */
    val plays: Int? = null,
    val last_watched_at: OffsetDateTime? = null,
    val last_updated_at: OffsetDateTime? = null,
    val reset_at: OffsetDateTime? = null,

    /** progress  */
    val aired: Int? = null,
    val completed: Int? = null,
    val hidden_seasons: List<Season>? = null,
    val next_episode: Episode? = null,
    val last_episode: Episode? = null,
)