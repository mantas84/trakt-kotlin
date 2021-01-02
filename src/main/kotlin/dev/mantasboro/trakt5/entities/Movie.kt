package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseEntity
import dev.mantasboro.trakt5.entities.base.BaseEntityData
import org.threeten.bp.LocalDate

@JsonClass(generateAdapter = false)
data class Movie(
    val year: Int? = null,
    val ids: MovieIds? = null,

    // extended info
    val certification: String? = null,
    val tagline: String? = null,

    /** Date in UTC time.  */
    val released: LocalDate? = null,
    val runtime: Int? = null,
    val trailer: String? = null,
    val homepage: String? = null,
    val language: String? = null,
    val genres: List<String>? = null,
    @Transient
    val data: BaseEntityData = BaseEntityData()
) : BaseEntity by data
