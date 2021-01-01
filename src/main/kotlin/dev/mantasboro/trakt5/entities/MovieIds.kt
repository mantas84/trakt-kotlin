package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseIds
import dev.mantasboro.trakt5.entities.base.BaseIdsData

@JsonClass(generateAdapter = false)
data class MovieIds(
    @Transient
    val data: BaseIdsData = BaseIdsData(),
    val slug: String? = null
) : BaseIds by data
