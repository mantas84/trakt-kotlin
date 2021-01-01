package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.LastActivityData
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class LastActivities(
    val all: OffsetDateTime? = null,
    val movies: LastActivityMore? = null,
    val episodes: LastActivityMore? = null,
    val shows: LastActivityData? = null,
    val seasons: LastActivityData? = null,
    val lists: ListsLastActivity? = null,
)
