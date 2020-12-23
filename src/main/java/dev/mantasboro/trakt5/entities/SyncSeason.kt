package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.entities.SyncEpisode
import dev.mantasboro.trakt5.enums.Rating
import dev.mantasboro.trakt5.entities.SyncSeason
import org.threeten.bp.OffsetDateTime
import java.util.ArrayList

class SyncSeason {
    var number: Int? = null
    var episodes: List<SyncEpisode>? = null
    var collected_at: OffsetDateTime? = null
    var watched_at: OffsetDateTime? = null
    var rated_at: OffsetDateTime? = null
    var rating: Rating? = null

    fun number(number: Int): SyncSeason {
        this.number = number
        return this
    }

    fun episodes(episodes: List<SyncEpisode>?): SyncSeason {
        this.episodes = episodes
        return this
    }

    fun episodes(episode: SyncEpisode): SyncSeason {
        val list = ArrayList<SyncEpisode>(1)
        list.add(episode)
        return episodes(list)
    }

    fun collectedAt(collectedAt: OffsetDateTime?): SyncSeason {
        collected_at = collectedAt
        return this
    }

    fun watchedAt(watchedAt: OffsetDateTime?): SyncSeason {
        watched_at = watchedAt
        return this
    }

    fun ratedAt(ratedAt: OffsetDateTime?): SyncSeason {
        rated_at = ratedAt
        return this
    }

    fun rating(rating: Rating?): SyncSeason {
        this.rating = rating
        return this
    }
}