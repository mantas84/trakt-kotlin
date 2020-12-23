package dev.mantasboro.trakt5.entities

import com.google.gson.annotations.SerializedName
import dev.mantasboro.trakt5.enums.*
import org.threeten.bp.OffsetDateTime

class SyncEpisode(
    var season: Int? = null,
    var number: Int? = null,

    var ids: EpisodeIds? = null,
    var collected_at: OffsetDateTime? = null,

    var watched_at: OffsetDateTime? = null,
    var rated_at: OffsetDateTime? = null,
    var rating: Rating? = null,
    var media_type: MediaType? = null,
    var resolution: Resolution? = null,
    var hdr: Hdr? = null,
    var audio: Audio? = null,
    var audio_channels: AudioChannels? = null,
    @SerializedName("3d")
    var is3d: Boolean? = null,
) {

    fun number(number: Int): SyncEpisode {
        this.number = number
        return this
    }

    fun season(season: Int): SyncEpisode {
        this.season = season
        return this
    }

    fun id(id: EpisodeIds?): SyncEpisode {
        ids = id
        return this
    }

    fun collectedAt(collectedAt: OffsetDateTime?): SyncEpisode {
        collected_at = collectedAt
        return this
    }

    fun watchedAt(watchedAt: OffsetDateTime?): SyncEpisode {
        watched_at = watchedAt
        return this
    }

    fun ratedAt(ratedAt: OffsetDateTime?): SyncEpisode {
        rated_at = ratedAt
        return this
    }

    fun rating(rating: Rating?): SyncEpisode {
        this.rating = rating
        return this
    }

    fun mediaType(media_type: MediaType?): SyncEpisode {
        this.media_type = media_type
        return this
    }

    fun resolution(resolution: Resolution?): SyncEpisode {
        this.resolution = resolution
        return this
    }

    fun hdr(hdr: Hdr?): SyncEpisode {
        this.hdr = hdr
        return this
    }

    fun audio(audio: Audio?): SyncEpisode {
        this.audio = audio
        return this
    }

    fun audioChannels(audio_channels: AudioChannels?): SyncEpisode {
        this.audio_channels = audio_channels
        return this
    }

    fun is3d(is3d: Boolean?): SyncEpisode {
        this.is3d = is3d
        return this
    }
}