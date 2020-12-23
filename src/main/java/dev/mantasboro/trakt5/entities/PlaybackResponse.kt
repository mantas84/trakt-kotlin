package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

class PlaybackResponse(
    val id: Long? = null,
    /** Playpack response only.  */
    val paused_at: OffsetDateTime? = null,
    /** Playpack response only.  */
    val type: String? = null,
    /** Scrobble response only.  */
    val action: String? = null,
    /** Scrobble response only.  */
    val sharing: ShareSettings? = null,
) : GenericProgress()
