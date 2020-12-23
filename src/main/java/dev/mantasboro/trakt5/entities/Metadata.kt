package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.entities.ListItemRank
import com.google.gson.annotations.SerializedName
import dev.mantasboro.trakt5.enums.*

data class Metadata(
    val media_type: MediaType?,
    val resolution: Resolution?,
    val hdr: Hdr? = null,
    val audio: Audio? = null,
    val audio_channels: AudioChannels? = null,

    @SerializedName("3d")
    val is3d: Boolean? = null,
)