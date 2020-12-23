package dev.mantasboro.trakt5;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import dev.mantasboro.trakt5.enums.*;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

public class TraktV2Helper {

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // Note: for enums always add a serializer, GSON does not use .toString() by default like retrofit!
        // Or use @SerializedName as an alternative.

        // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
        // such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
        builder.registerTypeAdapter(OffsetDateTime.class,
                (JsonDeserializer<OffsetDateTime>) (json, typeOfT, context) -> OffsetDateTime.parse(json.getAsString()));
        builder.registerTypeAdapter(OffsetDateTime.class,
                (JsonSerializer<OffsetDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        // dates are in ISO 8601 format as well
        builder.registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsString()));

        // rating
        builder.registerTypeAdapter(Rating.class,
                (JsonDeserializer<Rating>) (json, typeOfT, context) -> Rating.Companion.fromValue(json.getAsInt()));
        builder.registerTypeAdapter(Rating.class,
                (JsonSerializer<Rating>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getValue()));

        // status
        builder.registerTypeAdapter(Status.class,
                (JsonDeserializer<Status>) (json, typeOfT, context) -> Status.Companion.fromValue(json.getAsString()));

        // progress last activity
        builder.registerTypeAdapter(ProgressLastActivity.class,
                (JsonDeserializer<ProgressLastActivity>) (json, typeOfT, context) -> ProgressLastActivity.Companion.fromValue(json.getAsString()));

        // media type
        builder.registerTypeAdapter(MediaType.class,
                (JsonSerializer<MediaType>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        builder.registerTypeAdapter(MediaType.class,
                (JsonDeserializer<MediaType>) (json, typeOfT, context) -> MediaType.Companion.fromValue(json.getAsString()));

        // resolution
        builder.registerTypeAdapter(Resolution.class,
                (JsonSerializer<Resolution>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        builder.registerTypeAdapter(Resolution.class,
                (JsonDeserializer<Resolution>) (json, typeOfT, context) -> Resolution.Companion.fromValue(json.getAsString()));

        // hdr
        builder.registerTypeAdapter(Hdr.class,
                (JsonSerializer<Hdr>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        builder.registerTypeAdapter(Hdr.class,
                (JsonDeserializer<Hdr>) (json, typeOfT, context) -> Hdr.Companion.fromValue(json.getAsString()));

        // audio
        builder.registerTypeAdapter(Audio.class,
                (JsonSerializer<Audio>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        builder.registerTypeAdapter(Audio.class,
                (JsonDeserializer<Audio>) (json, typeOfT, context) -> Audio.Companion.fromValue(json.getAsString()));

        // audio channels
        builder.registerTypeAdapter(AudioChannels.class,
                (JsonSerializer<AudioChannels>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()));
        builder.registerTypeAdapter(AudioChannels.class,
                (JsonDeserializer<AudioChannels>) (json, typeOfT, context) -> AudioChannels.Companion.fromValue(json.getAsString()));

        return builder;
    }

}
