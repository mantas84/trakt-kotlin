package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.RatedShow
import dev.mantasboro.trakt5.entities.base.RatedShowData

@JsonClass(generateAdapter = false)
data class RatedEpisode(
    val episode: Episode?,
    @Transient
    val data: RatedShowData = RatedShowData(),
) : RatedShow by data
