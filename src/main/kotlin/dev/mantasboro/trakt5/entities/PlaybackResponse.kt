package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.entities.base.GenericProgress
import dev.mantasboro.trakt5.entities.base.GenericProgressData
import org.threeten.bp.OffsetDateTime

data class PlaybackResponse(
    val id: Long? = null,
    /** Playpack response only.  */
    val paused_at: OffsetDateTime? = null,
    /** Playpack response only.  */
    val type: String? = null,
    /** Scrobble response only.  */
    val action: String? = null,
    /** Scrobble response only.  */
    val sharing: ShareSettings? = null,
    private val data: GenericProgressData = GenericProgressData(),
) : GenericProgress by data
