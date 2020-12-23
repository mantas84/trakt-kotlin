package dev.mantasboro.trakt5.entities

class ScrobbleProgress(
    val app_version: String,
    val app_date: String,
    override val episode: SyncEpisode? = null,
    override val movie: SyncMovie? = null,
    override val progress: Double,
) : GenericProgress()
