package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class Credits(
    val cast: List<CastMember>? = null,
    val crew: Crew? = null,
)
