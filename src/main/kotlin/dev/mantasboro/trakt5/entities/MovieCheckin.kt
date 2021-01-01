package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseCheckin
import dev.mantasboro.trakt5.entities.base.BaseCheckinData

@JsonClass(generateAdapter = false)
data class MovieCheckin(
    @Transient
    val data: BaseCheckinData = BaseCheckinData(),
    val movie: SyncMovie
) : BaseCheckin by data
