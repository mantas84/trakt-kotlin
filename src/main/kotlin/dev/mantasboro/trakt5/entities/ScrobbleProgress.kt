package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.GenericProgress
import dev.mantasboro.trakt5.entities.base.GenericProgressData

@JsonClass(generateAdapter = false)

data class ScrobbleProgress(
    val app_version: String,
    val app_date: String,
    @Transient
    val data: GenericProgressData = GenericProgressData(),
) : GenericProgress by data
