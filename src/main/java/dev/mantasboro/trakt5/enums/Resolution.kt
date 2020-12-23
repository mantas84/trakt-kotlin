package dev.mantasboro.trakt5.enums

import java.util.*

enum class Resolution(private val value: String) : TraktEnum {

    UHD_4K("uhd_4k"),
    HD_1080P("hd_1080p"),
    HD_1080I("hd_1080i"),
    HD_720P("hd_720p"),
    SD_480P("sd_480p"),
    SD_480I("sd_480i"),
    SD_576P("sd_576p"),
    SD_576I("sd_576i");

    companion object {
        private val STRING_MAPPING: MutableMap<String, Resolution> = HashMap()

        fun fromValue(value: String): Resolution? {
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