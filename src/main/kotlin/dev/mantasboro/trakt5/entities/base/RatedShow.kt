package dev.mantasboro.trakt5.entities.base

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.Show
import dev.mantasboro.trakt5.enums.Rating
import org.threeten.bp.OffsetDateTime

interface RatedShow : BaseRatedEntity {
    var show: Show?
}

@JsonClass(generateAdapter = false)
data class RatedShowData(
    override var rated_at: OffsetDateTime? = null,
    override var rating: Rating? = null,
    override var show: Show? = null,
) : RatedShow
