package dev.mantasboro.trakt5.entities.base

interface BaseIds {
    var trakt: Int?
    var imdb: String?
    var tmdb: Int?
}

data class BaseIdsData(
    override var trakt: Int? = null,
    override var imdb: String? = null,
    override var tmdb: Int? = null
) : BaseIds
