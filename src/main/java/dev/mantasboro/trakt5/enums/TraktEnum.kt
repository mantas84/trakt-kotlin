package dev.mantasboro.trakt5.enums

interface TraktEnum {
    // Note: GSON does not use .toString() to serialize enums, but the key value.
    // Use @SerializedName for these enums (or register both serializer and deserializer in TraktV2Helper).
    /**
     * Return the value to be used by retrofit when building a request.
     */
    override fun toString(): String
}