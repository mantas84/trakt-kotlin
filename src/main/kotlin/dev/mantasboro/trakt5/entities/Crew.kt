package dev.mantasboro.trakt5.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Crew(
    val writing: List<CrewMember>? = null,
    val production: List<CrewMember>? = null,
    val directing: List<CrewMember>? = null,

    @Json(name = "costume & make-up")
    val costumeAndMakeUp: List<CrewMember>? = null,
    val art: List<CrewMember>? = null,
    val sound: List<CrewMember>? = null,
    val camera: List<CrewMember>? = null,
)
