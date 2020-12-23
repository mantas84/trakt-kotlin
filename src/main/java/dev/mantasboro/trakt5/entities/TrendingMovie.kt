package dev.mantasboro.trakt5.entities

class TrendingMovie(val movie: Movie, watchers: Int?) : BaseTrendingEntity(watchers)