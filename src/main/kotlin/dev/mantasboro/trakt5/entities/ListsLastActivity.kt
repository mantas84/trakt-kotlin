package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class ListsLastActivity(
    var liked_at: OffsetDateTime? = null,
    var updated_at: OffsetDateTime? = null,
    var commented_at: OffsetDateTime? = null,
)
