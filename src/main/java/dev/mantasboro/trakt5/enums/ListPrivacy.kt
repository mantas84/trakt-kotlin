package dev.mantasboro.trakt5.enums

enum class ListPrivacy(private val value: String) : TraktEnum {
    PRIVATE("private"),
    FRIENDS("friends"),
    PUBLIC("public");

    companion object {
        private val STRING_MAPPING: MutableMap<String, ListPrivacy> = HashMap()

        fun fromValue(value: String): ListPrivacy? = STRING_MAPPING[value]

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
