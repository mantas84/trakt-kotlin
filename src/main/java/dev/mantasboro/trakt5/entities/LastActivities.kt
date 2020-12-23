package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

data class LastActivities(
    val all: OffsetDateTime? = null,
    val movies: LastActivityMore? = null,
    val episodes: LastActivityMore? = null,
    val shows: LastActivity? = null,
    val seasons: LastActivity? = null,
    val lists: ListsLastActivity? = null,
)