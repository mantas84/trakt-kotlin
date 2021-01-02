package dev.mantasboro.trakt5.entities.base

import org.threeten.bp.OffsetDateTime

interface BaseEntity {
    var title: String?

    // extended info
    var overview: String?
    var rating: Double?
    var votes: Int?
    var updated_at: OffsetDateTime?
    var available_translations: List<String>?
}

data class BaseEntityData(
    override var title: String? = null,

    // extended info
    override var overview: String? = null,
    override var rating: Double? = null,
    override var votes: Int? = null,
    override var updated_at: OffsetDateTime? = null,
    override var available_translations: List<String>? = null,
) : BaseEntity
