package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.OffsetDateTime

class SyncShow {
    var ids: ShowIds? = null
    var seasons: List<SyncSeason>? = null
    var collected_at: OffsetDateTime? = null
    var watched_at: OffsetDateTime? = null
    var rated_at: OffsetDateTime? = null
    var rating: Rating? = null
    fun id(id: ShowIds?): SyncShow {
        ids = id
        return this
    }

    fun seasons(seasons: List<SyncSeason>?): SyncShow {
        this.seasons = seasons
        return this
    }

    fun seasons(season: SyncSeason): SyncShow {
        val list = ArrayList<SyncSeason>(1)
        list.add(season)
        return seasons(list)
    }

    fun collectedAt(collectedAt: OffsetDateTime?): SyncShow {
        collected_at = collectedAt
        return this
    }

    fun watchedAt(watchedAt: OffsetDateTime?): SyncShow {
        watched_at = watchedAt
        return this
    }

    fun ratedAt(ratedAt: OffsetDateTime?): SyncShow {
        rated_at = ratedAt
        return this
    }

    fun rating(rating: Rating?): SyncShow {
        this.rating = rating
        return this
    }
}
