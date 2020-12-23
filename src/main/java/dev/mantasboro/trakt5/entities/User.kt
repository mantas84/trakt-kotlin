package dev.mantasboro.trakt5.entities

import com.google.gson.annotations.SerializedName
import org.threeten.bp.OffsetDateTime

class User {
    val username: String? = null

    @SerializedName("private")
    val isPrivate: Boolean? = null
    val name: String? = null

    /** If a user is a regular VIP.  */
    val vip: Boolean? = null

    /** If a user is an execute producer.  */
    var vip_ep: Boolean? = null
    val ids: UserIds? = null

    // full
    val joined_at: OffsetDateTime? = null
    val location: String? = null
    val about: String? = null
    val gender: String? = null
    val age = 0
    val images: Images? = null

    data class UserIds(var slug: String? = null)
}