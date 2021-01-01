package dev.mantasboro.trakt5.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.enums.Audio
import dev.mantasboro.trakt5.enums.AudioChannels
import dev.mantasboro.trakt5.enums.Hdr
import dev.mantasboro.trakt5.enums.MediaType
import dev.mantasboro.trakt5.enums.Resolution

@JsonClass(generateAdapter = true)
data class Metadata(
    val media_type: MediaType?,
    val resolution: Resolution?,
    val hdr: Hdr? = null,
    val audio: Audio? = null,
    val audio_channels: AudioChannels? = null,

    @Json(name = "3d")
    val is3d: Boolean? = null,
)
