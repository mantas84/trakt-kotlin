package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
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
