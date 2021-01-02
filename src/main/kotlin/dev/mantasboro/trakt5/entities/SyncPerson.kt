package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SyncPerson(
    val ids: PersonIds? = null,
    val name: String? = null,
)
