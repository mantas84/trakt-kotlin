package dev.mantasboro.trakt5

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import dev.mantasboro.trakt5.enums.Audio
import dev.mantasboro.trakt5.enums.AudioChannels
import dev.mantasboro.trakt5.enums.Hdr
import dev.mantasboro.trakt5.enums.ListPrivacy
import dev.mantasboro.trakt5.enums.MediaType
import dev.mantasboro.trakt5.enums.ProgressLastActivity
import dev.mantasboro.trakt5.enums.Rating
import dev.mantasboro.trakt5.enums.Rating.Companion.fromValue
import dev.mantasboro.trakt5.enums.Resolution
import dev.mantasboro.trakt5.enums.SortBy
import dev.mantasboro.trakt5.enums.SortHow
import dev.mantasboro.trakt5.enums.Status
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import java.lang.reflect.Type

object TraktV2Helper {
    // Note: for enums always add a serializer, GSON does not use .toString() by default like retrofit!
    // Or use @SerializedName as an alternative.

    // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
    // such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
    // dates are in ISO 8601 format as well

    // rating

    // status

    // progress last activity

    // media type

    // resolution

    // hdr

    // audio

    // audio channels
    val gsonBuilder: GsonBuilder
        get() {
            val builder = GsonBuilder()

            // Note: for enums always add a serializer, GSON does not use .toString() by default like retrofit!
            // Or use @SerializedName as an alternative.

            // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
            // such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
            builder.registerTypeAdapter(OffsetDateTime::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    OffsetDateTime.parse(json.asString)
                } as JsonDeserializer<OffsetDateTime>)
            builder.registerTypeAdapter(OffsetDateTime::class.java,
                JsonSerializer { src: OffsetDateTime, typeOfSrc: Type?, context: JsonSerializationContext? ->
                    JsonPrimitive(src.toString())
                } as JsonSerializer<OffsetDateTime>)
            // dates are in ISO 8601 format as well
            builder.registerTypeAdapter(LocalDate::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    LocalDate.parse(json.asString)
                } as JsonDeserializer<LocalDate>)

            // rating
            builder.registerTypeAdapter(Rating::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    fromValue(json.asInt)
                } as JsonDeserializer<Rating>)
            builder.registerTypeAdapter(Rating::class.java,
                JsonSerializer { src: Rating, typeOfSrc: Type?, context: JsonSerializationContext? -> JsonPrimitive(src.value) } as JsonSerializer<Rating>)

            // status
            builder.registerTypeAdapter(
                Status::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    Status.fromValue(json.asString)
                })

            // progress last activity
            builder.registerTypeAdapter(ProgressLastActivity::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    ProgressLastActivity.fromValue(json.asString)
                })

            // media type
            builder.registerTypeAdapter(MediaType::class.java,
                JsonSerializer { src: MediaType, typeOfSrc: Type?, context: JsonSerializationContext? ->
                    JsonPrimitive(src.toString())
                } as JsonSerializer<MediaType>)
            builder.registerTypeAdapter(
                MediaType::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    MediaType.fromValue(json.asString)
                })

            // resolution
            builder.registerTypeAdapter(Resolution::class.java,
                JsonSerializer { src: Resolution, typeOfSrc: Type?, context: JsonSerializationContext? ->
                    JsonPrimitive(src.toString())
                } as JsonSerializer<Resolution>)
            builder.registerTypeAdapter(
                Resolution::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    Resolution.fromValue(json.asString)
                })

            // hdr
            builder.registerTypeAdapter(Hdr::class.java,
                JsonSerializer { src: Hdr, typeOfSrc: Type?, context: JsonSerializationContext? -> JsonPrimitive(src.toString()) } as JsonSerializer<Hdr>)
            builder.registerTypeAdapter(Hdr::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    Hdr.fromValue(json.asString)
                })

            // audio
            builder.registerTypeAdapter(Audio::class.java,
                JsonSerializer { src: Audio, typeOfSrc: Type?, context: JsonSerializationContext? -> JsonPrimitive(src.toString()) } as JsonSerializer<Audio>)
            builder.registerTypeAdapter(Audio::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    Audio.fromValue(json.asString)
                })

            // audio channels
            builder.registerTypeAdapter(AudioChannels::class.java,
                JsonSerializer { src: AudioChannels, typeOfSrc: Type?, context: JsonSerializationContext? ->
                    JsonPrimitive(src.toString())
                } as JsonSerializer<AudioChannels>)
            builder.registerTypeAdapter(AudioChannels::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    AudioChannels.fromValue(json.asString)
                })

            // ListPrivacy
            builder.registerTypeAdapter(ListPrivacy::class.java,
                JsonSerializer { src: ListPrivacy, typeOfSrc: Type?, context: JsonSerializationContext? ->
                    JsonPrimitive(src.toString())
                } as JsonSerializer<ListPrivacy>)
            builder.registerTypeAdapter(ListPrivacy::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    ListPrivacy.fromValue(json.asString)
                })

            // SortBy
            builder.registerTypeAdapter(SortBy::class.java,
                JsonSerializer { src: SortBy, typeOfSrc: Type?, context: JsonSerializationContext? ->
                    JsonPrimitive(src.toString())
                } as JsonSerializer<SortBy>)
            builder.registerTypeAdapter(SortBy::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    SortBy.fromValue(json.asString)
                })

            // SortHow
            builder.registerTypeAdapter(SortHow::class.java,
                JsonSerializer { src: SortHow, typeOfSrc: Type?, context: JsonSerializationContext? ->
                    JsonPrimitive(src.toString())
                } as JsonSerializer<SortHow>)
            builder.registerTypeAdapter(SortHow::class.java,
                JsonDeserializer { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext? ->
                    SortHow.fromValue(json.asString)
                })
            return builder
        }
}
