package dev.mantasboro.trakt5.entities

import com.google.gson.annotations.SerializedName

data class Crew(
    val writing: List<CrewMember>? = null,
    val production: List<CrewMember>? = null,
    val directing: List<CrewMember>? = null,

    @SerializedName("costume & make-up")
    val costumeAndMakeUp: List<CrewMember>? = null,
    val art: List<CrewMember>? = null,
    val sound: List<CrewMember>? = null,
    val camera: List<CrewMember>? = null,
)
