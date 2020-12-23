package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

data class BaseEpisode(
    val number: Int? = null,
    /** collection  */
    val collected_at: OffsetDateTime? = null,
    /** watched  */
    val plays: Int? = null,
    val last_watched_at: OffsetDateTime? = null,
    /** progress  */
    val completed: Boolean? = null,
    val metadata: Metadata? = null,
)
