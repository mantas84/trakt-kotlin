package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.enums.Status
import org.threeten.bp.OffsetDateTime

@Suppress("LongParameterList")
class Show(
    var year: Int? = null,
    var ids: ShowIds? = null,

    // extended info
    var first_aired: OffsetDateTime? = null,
    var airs: Airs? = null,
    var runtime: Int? = null,
    var certification: String? = null,
    var network: String? = null,
    var country: String? = null,
    var trailer: String? = null,
    var homepage: String? = null,
    var status: Status? = null,
    var language: String? = null,
    var genres: List<String>? = null,
) : BaseEntity()
