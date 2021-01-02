package dev.mantasboro.trakt5.entities.base

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

interface LastActivity {
    var rated_at: OffsetDateTime?
    var watchlisted_at: OffsetDateTime?
    var commented_at: OffsetDateTime?
}

@JsonClass(generateAdapter = true)
data class LastActivityData(
    override var rated_at: OffsetDateTime? = null,
    override var watchlisted_at: OffsetDateTime? = null,
    override var commented_at: OffsetDateTime? = null,
) : LastActivity
