package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseEntity
import dev.mantasboro.trakt5.entities.base.BaseEntityData
import org.threeten.bp.OffsetDateTime

@Suppress("LongParameterList")
@JsonClass(generateAdapter = false)
data class Episode(
    val season: Int? = null,
    val number: Int? = null,
    val ids: EpisodeIds? = null,

    // extended info
    val number_abs: Int? = null,
    val first_aired: OffsetDateTime? = null,
    val comment_count: Int? = null,
    val runtime: Int? = null,
    @Transient
    val data: BaseEntityData = BaseEntityData()
) : BaseEntity by data
