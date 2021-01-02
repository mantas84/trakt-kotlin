package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListIds(
    var trakt: Int? = null,
    var slug: String? = null,
)
