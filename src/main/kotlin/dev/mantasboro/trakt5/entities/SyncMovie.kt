package dev.mantasboro.trakt5.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.enums.Audio
import dev.mantasboro.trakt5.enums.AudioChannels
import dev.mantasboro.trakt5.enums.Hdr
import dev.mantasboro.trakt5.enums.MediaType
import dev.mantasboro.trakt5.enums.Rating
import dev.mantasboro.trakt5.enums.Resolution
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = false)
class SyncMovie {
    var ids: MovieIds? = null
    var collected_at: OffsetDateTime? = null
    var watched_at: OffsetDateTime? = null
    var rated_at: OffsetDateTime? = null
    var rating: Rating? = null
    var media_type: MediaType? = null
    var resolution: Resolution? = null
    var hdr: Hdr? = null
    var audio: Audio? = null
    var audio_channels: AudioChannels? = null

    @Json(name = "3d")
    var is3d: Boolean? = null
    fun id(id: MovieIds?): SyncMovie {
        ids = id
        return this
    }

    fun collectedAt(collectedAt: OffsetDateTime?): SyncMovie {
        collected_at = collectedAt
        return this
    }

    fun watchedAt(watchedAt: OffsetDateTime?): SyncMovie {
        watched_at = watchedAt
        return this
    }

    fun ratedAt(ratedAt: OffsetDateTime?): SyncMovie {
        rated_at = ratedAt
        return this
    }

    fun rating(rating: Rating?): SyncMovie {
        this.rating = rating
        return this
    }

    fun mediaType(media_type: MediaType?): SyncMovie {
        this.media_type = media_type
        return this
    }

    fun resolution(resolution: Resolution?): SyncMovie {
        this.resolution = resolution
        return this
    }

    fun hdr(hdr: Hdr?): SyncMovie {
        this.hdr = hdr
        return this
    }

    fun audio(audio: Audio?): SyncMovie {
        this.audio = audio
        return this
    }

    fun audioChannels(audio_channels: AudioChannels?): SyncMovie {
        this.audio_channels = audio_channels
        return this
    }

    fun is3d(is3d: Boolean?): SyncMovie {
        this.is3d = is3d
        return this
    }
}
