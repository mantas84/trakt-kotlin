package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ratings(
    val rating: Double? = null,
    val votes: Int? = null,
    val distribution: Map<String, Int>? = null,
)
