package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseRatedEntity
import dev.mantasboro.trakt5.entities.base.BaseRatedEntityData

@JsonClass(generateAdapter = false)
data class RatedMovie(
    val movie: Movie? = null,
    @Transient
    val data: BaseRatedEntityData = BaseRatedEntityData()
) : BaseRatedEntity by data
