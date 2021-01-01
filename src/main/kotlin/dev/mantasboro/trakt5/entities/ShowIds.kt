package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseIds
import dev.mantasboro.trakt5.entities.base.BaseIdsData

@JsonClass(generateAdapter = false)
data class ShowIds(
    val slug: String? = null,
    val tvdb: Int? = null,
    val tvrage: Int? = null,
    @Transient
    val data: BaseIdsData = BaseIdsData()
) : BaseIds by data
