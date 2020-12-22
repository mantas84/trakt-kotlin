package dev.mantasboro.trakt5.entities;

import dev.mantasboro.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

public class BaseRatedEntity {

    public OffsetDateTime rated_at;
    public Rating rating;

}
