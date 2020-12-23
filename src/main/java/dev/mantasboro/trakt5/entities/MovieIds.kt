package dev.mantasboro.trakt5.entities

class MovieIds(
    trakt: Int? = null,
    imdb: String? = null,
    tmdb: Int? = null,
    val slug: String? = null
) : BaseIds(trakt, imdb, tmdb)
