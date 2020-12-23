package dev.mantasboro.trakt5.enums

import java.util.*

enum class Audio(private val value: String) : TraktEnum {
    LPCM("lpcm"),
    MP3("mp3"),
    MP2("mp2"),
    AAC("aac"),
    OGG("ogg"),
    OGG_OPUS("ogg_opus"),
    WMA("wma"),
    FLAC("flac"),
    DTS("dts"),
    DTS_MA("dts_ma"),
    DTS_HR("dts_hr"),
    DTS_X("dts_x"),
    AURO_3D("auro_3d"),
    DOLBY_DIGITAL("dolby_digital"),
    DOLBY_DIGITAL_PLUS("dolby_digital_plus"),
    DOLBY_DIGITAL_PLUS_ATMOS("dolby_digital_plus_atmos"),
    DOLBY_ATMOS("dolby_atmos"),
    DOLBY_TRUEHD("dolby_truehd"),
    DOLBY_PROLOGIC("dolby_prologic");

    companion object {
        private val STRING_MAPPING: MutableMap<String, Audio> = HashMap()

        fun fromValue(value: String): Audio? {
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