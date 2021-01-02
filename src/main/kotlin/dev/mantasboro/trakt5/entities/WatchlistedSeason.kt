package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class WatchlistedSeason(
    var listed_at: OffsetDateTime? = null,
    var season: Season? = null,
    var show: Show? = null,
)
