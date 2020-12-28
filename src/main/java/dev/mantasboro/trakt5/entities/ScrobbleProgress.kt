package dev.mantasboro.trakt5.entities

class ScrobbleProgress(
    val app_version: String,
    val app_date: String,
    episode: SyncEpisode? = null,
    movie: SyncMovie? = null,
    progress: Double,
) : GenericProgress(episode = episode, movie = movie, progress = progress, show = null)
