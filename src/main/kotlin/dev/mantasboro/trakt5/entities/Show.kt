package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.base.BaseEntity
import dev.mantasboro.trakt5.entities.base.BaseEntityData
import dev.mantasboro.trakt5.enums.Status
import org.threeten.bp.OffsetDateTime

@Suppress("LongParameterList")
@JsonClass(generateAdapter = false)
data class Show(
    val year: Int? = null,
    val ids: ShowIds? = null,

    // extended info
    val first_aired: OffsetDateTime? = null,
    val airs: Airs? = null,
    val runtime: Int? = null,
    val certification: String? = null,
    val network: String? = null,
    val country: String? = null,
    val trailer: String? = null,
    val homepage: String? = null,
    val status: Status? = null,
    val language: String? = null,
    val genres: List<String>? = null,
    @Transient
    val data: BaseEntityData = BaseEntityData()
) : BaseEntity by data
