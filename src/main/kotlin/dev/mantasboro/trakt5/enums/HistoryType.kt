package dev.mantasboro.trakt5.enums

enum class HistoryType(private val value: String) : TraktEnum {

    MOVIES("movies"),
    SHOWS("shows"),
    SEASONS("seasons"),
    EPISODES("episodes");

    override fun toString(): String {
        return value
    }
}
