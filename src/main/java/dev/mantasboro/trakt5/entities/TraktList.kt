package dev.mantasboro.trakt5.entities

import dev.mantasboro.trakt5.enums.ListPrivacy
import dev.mantasboro.trakt5.enums.SortBy
import dev.mantasboro.trakt5.enums.SortHow
import org.threeten.bp.OffsetDateTime

class TraktList {
    var ids: ListIds? = null
    var name: String? = null
    var description: String? = null
    var privacy: ListPrivacy? = null
    var display_numbers: Boolean? = null
    var allow_comments: Boolean? = null
    var sort_by: SortBy? = null
    var sort_how: SortHow? = null
    var created_at: OffsetDateTime? = null
    var updated_at: OffsetDateTime? = null
    var item_count: Int? = null
    var comment_count: Int? = null
    var likes: Int? = null
    var user: User? = null

    fun id(id: ListIds?): TraktList {
        ids = id
        return this
    }

    fun name(name: String?): TraktList {
        this.name = name
        return this
    }

    fun description(description: String?): TraktList {
        this.description = description
        return this
    }

    fun privacy(privacy: ListPrivacy?): TraktList {
        this.privacy = privacy
        return this
    }

    fun displayNumbers(displayNumbers: Boolean): TraktList {
        display_numbers = displayNumbers
        return this
    }

    fun allowComments(allowComments: Boolean): TraktList {
        allow_comments = allowComments
        return this
    }

    fun sortBy(sortBy: SortBy?): TraktList {
        sort_by = sortBy
        return this
    }

    fun sortHow(sortHow: SortHow?): TraktList {
        sort_how = sortHow
        return this
    }
}