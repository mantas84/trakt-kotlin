package dev.mantasboro.trakt5.enums

import java.util.*

enum class MediaType(private val value: String) : TraktEnum {

    DIGITAL("digital"),
    BLURAY("bluray"),
    HDDVD("hddvd"),
    DVD("dvd"),
    VCD("vcd"),
    VHS("vhs"),
    BETAMAX("betamax"),
    LASERDISC("laserdisc");

    companion object {
        private val STRING_MAPPING: MutableMap<String, MediaType> = HashMap()

        fun fromValue(value: String): MediaType? {
            return STRING_MAPPING[value]
        }

        init {
            for (via in values()) {
                STRING_MAPPING[via.toString()] = via
            }
        }
    }

    override fun toString(): String {
        return value
    }
}