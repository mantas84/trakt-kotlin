package dev.mantasboro.trakt5.enums

import java.util.*

enum class AudioChannels(private val value: String) : TraktEnum {
    CH1_0("1.0"),
    CH2_0("2.0"),
    CH2_1("2.1"),
    CH3_0("3.0"),
    CH3_1("3.1"),
    CH4_0("4.0"),
    CH4_1("4.1"),
    CH5_0("5.0"),
    CH5_1("5.1"),
    CH5_1_2("5.1.2"),
    CH5_1_4("5.1.4"),
    CH6_1("6.1"),
    CH7_1("7.1"),
    CH7_1_2("7.1.2"),
    CH7_1_4("7.1.4"),
    CH9_1("9.1"),
    CH10_1("10.1");

    companion object {
        private val STRING_MAPPING: MutableMap<String, AudioChannels> = HashMap()

        fun fromValue(value: String): AudioChannels? = STRING_MAPPING[value]

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