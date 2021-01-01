package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseIds
import dev.mantasboro.trakt5.entities.base.BaseIdsData

@JsonClass(generateAdapter = false)
data class PersonIds(
    val slug: String? = null,
    val tvrage: String? = null,
    @Transient
    val data: BaseIdsData = BaseIdsData()
) : BaseIds by data
