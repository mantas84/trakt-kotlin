package dev.mantasboro.trakt5.entities

abstract class GenericProgress(
    open val episode: SyncEpisode? = null,
    val show: SyncShow? = null,
    open val movie: SyncMovie? = null,
    open val progress: Double? = null,
)
