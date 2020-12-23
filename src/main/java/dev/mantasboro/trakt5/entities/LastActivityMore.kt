package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

class LastActivityMore : LastActivity() {
    val watched_at: OffsetDateTime? = null
    val collected_at: OffsetDateTime? = null
    val paused_at: OffsetDateTime? = null
    val hidden_at: OffsetDateTime? = null
}