package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseCheckin
import dev.mantasboro.trakt5.entities.base.BaseCheckinData

@Suppress("LongParameterList")
@JsonClass(generateAdapter = false)
data class EpisodeCheckin(
    @Transient
    val data: BaseCheckinData = BaseCheckinData(),
    val show: Show? = null,
    val episode: SyncEpisode
) : BaseCheckin by data
