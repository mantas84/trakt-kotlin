package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserSlug(userSlug: String) {
    val userSlug: String

    /**
     * User slug to pass to the API, performs some simple null and empty checks.
     *
     * @param userSlug A user slug as returned by the trakt API: [User.UserIds].
     * @see .ME
     */
    init {
        require(userSlug.isNotBlank()) { "trakt user slug can not be empty." }
        this.userSlug = userSlug.trim()
    }

    companion object {
        /**
         * Special user slug for the current user (determined by auth data).
         */
        val ME = UserSlug("me")

        /**
         * Encodes the username returned from trakt so it is API compatible (currently replaces "." and spaces with "-").
         */
        fun fromUsername(username: String): UserSlug {
            // trakt encodes some special chars in usernames
            // - points "." as a dash "-"
            // - spaces " " as a dash "-"
            // - multiple dashes are reduced to one
            val slug = username
                .replace(".", "-")
                .replace(" ", "-")
                .replace("(-)+".toRegex(), "-")
            return UserSlug(slug)
        }
    }

    override fun toString(): String = userSlug
}
