package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class BaseMovie(
    val movie: Movie? = null,
    val collected_at: OffsetDateTime? = null,
    val last_watched_at: OffsetDateTime? = null,
    val last_updated_at: OffsetDateTime? = null,
    val listed_at: OffsetDateTime? = null,
    val plays: Int = 0,
    val metadata: Metadata? = null,
)
