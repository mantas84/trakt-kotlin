package dev.mantasboro.trakt5.entities

class EpisodeCheckin constructor(
    val show: Show? = null,
    val episode: SyncEpisode,
    sharing: ShareSettings? = null,
    message: String? = null,
    venue_id: String? = null,
    venue_name: String? = null,
    app_version: String,
    app_date: String,
) : BaseCheckin(
    sharing,
    message,
    venue_id,
    venue_name,
    app_version,
    app_date,
)