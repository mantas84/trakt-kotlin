package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
    val avatar: ImageSizes? = null

)

data class ImageSizes(val full: String? = null)
