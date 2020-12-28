package dev.mantasboro.trakt5.entities

abstract class GenericProgress(
    val episode: SyncEpisode? = null,
    val show: SyncShow? = null,
    val movie: SyncMovie? = null,
    val progress: Double? = null,
)
