package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class WatchlistedEpisode(
    var listed_at: OffsetDateTime? = null,
    var episode: Episode? = null,
    var show: Show? = null,
)
