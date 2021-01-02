package dev.mantasboro.trakt5.entities.base

import dev.mantasboro.trakt5.entities.ShareSettings
import org.threeten.bp.OffsetDateTime

interface BaseCheckinResponse {
    var watched_at: OffsetDateTime?
    var sharing: ShareSettings?
}

data class BaseCheckinResponseData(
    override var watched_at: OffsetDateTime? = null,
    override var sharing: ShareSettings? = null
) : BaseCheckinResponse
