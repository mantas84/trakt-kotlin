package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.RatedShow
import dev.mantasboro.trakt5.entities.base.RatedShowData

@JsonClass(generateAdapter = false)
data class RatedSeason(
    val season: Season?,
    @Transient
    val data: RatedShowData = RatedShowData(),
) : RatedShow by data
