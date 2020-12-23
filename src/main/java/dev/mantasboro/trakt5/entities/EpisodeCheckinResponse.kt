package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

class EpisodeCheckinResponse(
    var episode: Episode? = null,
    var show: Show? = null,
    watched_at: OffsetDateTime? = null,
    sharing: ShareSettings? = null
) : BaseCheckinResponse(watched_at, sharing)