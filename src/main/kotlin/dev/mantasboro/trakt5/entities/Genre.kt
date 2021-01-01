package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    var name: String?,
    var slug: String?,
)
