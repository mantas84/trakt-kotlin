package dev.mantasboro.trakt5.entities.base

import com.squareup.moshi.JsonClass

interface Translation {
    var language: String?
    var title: String?
    var overview: String?
}

@JsonClass(generateAdapter = true)
data class TranslationData(
    override var language: String? = null,
    override var title: String? = null,
    override var overview: String? = null,
) : Translation
