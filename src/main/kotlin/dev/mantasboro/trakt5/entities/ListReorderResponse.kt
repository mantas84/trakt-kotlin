package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListReorderResponse(
    val updated: Int? = null,
    val skipped_ids: List<Long>? = null,
)
