package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseIds
import dev.mantasboro.trakt5.entities.base.BaseIdsData

@JsonClass(generateAdapter = false)
data class EpisodeIds(
    @Transient
    val data: BaseIdsData = BaseIdsData(),
    val tvdb: Int? = null,
    val tvrage: Int? = null,
) : BaseIds by data
