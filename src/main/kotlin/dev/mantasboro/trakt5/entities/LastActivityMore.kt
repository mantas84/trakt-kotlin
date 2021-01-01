package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.LastActivity
import dev.mantasboro.trakt5.entities.base.LastActivityData
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = false)
data class LastActivityMore(
    val watched_at: OffsetDateTime? = null,
    val collected_at: OffsetDateTime? = null,
    val paused_at: OffsetDateTime? = null,
    val hidden_at: OffsetDateTime? = null,
    @Transient
    val data: LastActivityData = LastActivityData(),
) : LastActivity by data
