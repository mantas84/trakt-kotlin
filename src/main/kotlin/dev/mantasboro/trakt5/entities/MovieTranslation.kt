package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.Translation
import dev.mantasboro.trakt5.entities.base.TranslationData

@JsonClass(generateAdapter = false)
data class MovieTranslation(
    val tagline: String?,
    @Transient
    val data: TranslationData = TranslationData()
) : Translation by data
