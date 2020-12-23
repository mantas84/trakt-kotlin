package dev.mantasboro.trakt5.entities

data class SyncStats(
    val movies: Int? = null,
    val shows: Int? = null,
    val seasons: Int? = null,
    val episodes: Int? = null,
    val people: Int? = null,
)
