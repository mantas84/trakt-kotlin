package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseCheckinResponse
import dev.mantasboro.trakt5.entities.base.BaseCheckinResponseData

@JsonClass(generateAdapter = false)
data class MovieCheckinResponse(
    @Transient
    val data: BaseCheckinResponseData = BaseCheckinResponseData(),
    val movie: Movie? = null,
) : BaseCheckinResponse by data
