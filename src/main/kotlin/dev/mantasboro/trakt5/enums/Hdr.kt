package dev.mantasboro.trakt5.enums

enum class Hdr(private val value: String) : TraktEnum {

    DOLBY_VISION("dolby_vision"),
    HDR10("hdr10"),
    HDR10_PLUS("hdr10_plus"),
    HLG("hlg");

    companion object {
        private val STRING_MAPPING: MutableMap<String, Hdr> = HashMap()

        fun fromValue(value: String): Hdr? {
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
