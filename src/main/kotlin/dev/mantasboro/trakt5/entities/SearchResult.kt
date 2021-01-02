package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(
    val type: String? = null,
    val score: Double? = null,
    val movie: Movie? = null,
    val show: Show? = null,
    val episode: Episode? = null,
    val person: Person? = null,
    val list: TraktList? = null,
)
