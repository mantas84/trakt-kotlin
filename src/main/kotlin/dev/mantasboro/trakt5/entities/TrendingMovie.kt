package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseTrendingEntity
import dev.mantasboro.trakt5.entities.base.BaseTrendingEntityData

@JsonClass(generateAdapter = false)
class TrendingMovie(
    val movie: Movie,
    @Transient
    val data: BaseTrendingEntityData = BaseTrendingEntityData()
) : BaseTrendingEntity by data
