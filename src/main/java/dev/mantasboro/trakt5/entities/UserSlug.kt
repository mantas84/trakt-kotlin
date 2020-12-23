package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.entities.UserSlug
import java.lang.IllegalArgumentException

class UserSlug(userSlug: String?) {
    private val userSlug: String

    override fun toString(): String {
        return userSlug
    }

    /**
     * User slug to pass to the API, performs some simple null and empty checks.
     *
     * @param userSlug A user slug as returned by the trakt API: [User.UserIds].
     * @see .ME
     */
    init {
        var userSlug = userSlug
        requireNotNull(userSlug) { "trakt user slug can not be null." }
        userSlug = userSlug.trim { it <= ' ' }
        require(userSlug.isNotEmpty()) { "trakt user slug can not be empty." }
        this.userSlug = userSlug
    }

    companion object {
        /**
         * Special user slug for the current user (determined by auth data).
         */
        val ME = UserSlug("me")

        /**
         * Encodes the username returned from trakt so it is API compatible (currently replaces "." and spaces with "-").
         */
        fun fromUsername(username: String?): UserSlug {
            requireNotNull(username) { "trakt username can not be null." }
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
}