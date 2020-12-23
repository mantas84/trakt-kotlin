package dev.mantasboro.trakt5.entities

import org.threeten.bp.OffsetDateTime

@Suppress("LongParameterList")
class Episode(
    val season: Int? = null,
    val number: Int? = null,
    val ids: EpisodeIds? = null,

    // extended info
    val number_abs: Int? = null,
    val first_aired: OffsetDateTime? = null,
    val comment_count: Int? = null,
    val runtime: Int? = null,
) : BaseEntity()
