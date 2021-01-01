package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SyncResponse(
    val added: SyncStats? = null,
    val existing: SyncStats? = null,
    val deleted: SyncStats? = null,
    val not_found: SyncErrors? = null,
)
