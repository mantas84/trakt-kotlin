package dev.mantasboro.trakt5.entities.base

interface BaseTrendingEntity {
    var watchers: Int?
}

data class BaseTrendingEntityData(override var watchers: Int? = null) : BaseTrendingEntity
