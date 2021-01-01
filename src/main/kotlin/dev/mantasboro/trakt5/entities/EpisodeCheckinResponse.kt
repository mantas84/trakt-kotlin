package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseCheckinResponse
import dev.mantasboro.trakt5.entities.base.BaseCheckinResponseData

@JsonClass(generateAdapter = false)
class EpisodeCheckinResponse(
    @Transient
    val data: BaseCheckinResponseData = BaseCheckinResponseData(),
    var episode: Episode? = null,
    var show: Show? = null
) : BaseCheckinResponse by data
