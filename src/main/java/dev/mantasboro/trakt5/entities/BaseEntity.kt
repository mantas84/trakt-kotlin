package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

abstract class BaseEntity(
    var title: String? = null,

    // extended info
    val overview: String? = null,
    val rating: Double? = null,
    val votes: Int? = null,
    val updated_at: OffsetDateTime? = null,
    val available_translations: List<String>? = null,
)
