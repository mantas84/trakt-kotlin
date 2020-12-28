package dev.mantasboro.trakt5.enums

enum class SortHow(private val value: String) {
    ASC("asc"),
    DESC("desc");

    companion object {
        private val STRING_MAPPING: MutableMap<String, SortHow> = HashMap()

        fun fromValue(value: String): SortHow? = STRING_MAPPING[value]

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
