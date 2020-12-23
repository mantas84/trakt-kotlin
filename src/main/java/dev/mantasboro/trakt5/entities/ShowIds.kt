package dev.mantasboro.trakt5.entities

class ShowIds(
    val slug: String? = null,
    val tvdb: Int? = null,
    val tvrage: Int? = null,
    trakt: Int? = null,
    imdb: String? = null,
    tmdb: Int? = null
) : BaseIds(trakt, imdb, tmdb)
