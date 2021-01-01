package dev.mantasboro.trakt5

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime

class TraktV2Adapters {

    @ToJson
    fun offsetDateTimeToJson(dateTime: OffsetDateTime): String {
        return dateTime.toString()
    }

    @FromJson
    fun offsetDateTimeFromJson(json: String): OffsetDateTime {
        return OffsetDateTime.parse(json)
    }

    @ToJson
    fun localDateToJson(dateTime: LocalDate): String {
        return dateTime.toString()
    }

    @FromJson
    fun localDateFromJson(json: String): LocalDate {
        return LocalDate.parse(json)
    }

    @ToJson
    fun ratingToJson(rating: Rating): Int {
        return when (rating) {
            Rating.WEAKSAUCE -> 1
            Rating.TERRIBLE -> 2
            Rating.BAD -> 3
            Rating.POOR -> 4
            Rating.MEH -> 5
            Rating.FAIR -> 6
            Rating.GOOD -> 7
            Rating.GREAT -> 8
            Rating.SUPERB -> 9
            Rating.TOTALLYNINJA -> 10
        }
    }

    @FromJson
    fun ratingFromJson(json: Int): Rating {
        return when (json) {
            1 -> Rating.WEAKSAUCE
            2 -> Rating.TERRIBLE
            3 -> Rating.BAD
            4 -> Rating.POOR
            5 -> Rating.MEH
            6 -> Rating.FAIR
            7 -> Rating.GOOD
            8 -> Rating.GREAT
            9 -> Rating.SUPERB
            10 -> Rating.TOTALLYNINJA
            else -> throw IllegalArgumentException("Name $json does not match any of the enum constants defined in the class")
        }
    }
}
