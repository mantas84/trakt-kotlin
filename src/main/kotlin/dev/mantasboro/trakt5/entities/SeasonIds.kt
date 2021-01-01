package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonIds(
    var tvdb: Int? = null,
    var tmdb: Int? = null,
    var trakt: Int? = null,
    var tvrage: Int? = null,
)
