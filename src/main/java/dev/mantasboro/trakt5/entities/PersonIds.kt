package dev.mantasboro.trakt5.entities

class PersonIds(
    val slug: String? = null,
    val tvrage: String? = null,
    trakt: Int? = null,
    imdb: String? = null,
    tmdb: Int? = null
) : BaseIds(trakt, imdb, tmdb)