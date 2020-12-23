package dev.mantasboro.trakt5.entities

data class ListReorderResponse(
    val updated: Int? = null,
    val skipped_ids: List<Long>? = null,
)