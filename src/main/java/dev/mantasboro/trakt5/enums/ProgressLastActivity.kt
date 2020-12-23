package dev.mantasboro.trakt5.enums

import java.util.*

enum class ProgressLastActivity(private val value: String) : TraktEnum {

    COLLECTED("collected"),
    WATCHED("watched");

    companion object {
        private val STRING_MAPPING: MutableMap<String, ProgressLastActivity> = HashMap()
        fun fromValue(value: String): ProgressLastActivity? {
            return STRING_MAPPING[value.toUpperCase(Locale.ROOT)]
        }

        init {
            for (via in values()) {
                STRING_MAPPING[via.toString().toUpperCase(Locale.ROOT)] = via
            }
        }
    }

    override fun toString(): String {
        return value
    }
}