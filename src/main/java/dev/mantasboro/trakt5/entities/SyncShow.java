package dev.mantasboro.trakt5.entities;

import dev.mantasboro.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;

public class SyncShow {

    public ShowIds ids;
    public List<SyncSeason> seasons;

    public OffsetDateTime collected_at;
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    public SyncShow id(ShowIds id) {
        this.ids = id;
        return this;
    }

    public SyncShow seasons(List<SyncSeason> seasons) {
        this.seasons = seasons;
        return this;
    }

    public SyncShow seasons(SyncSeason season) {
        ArrayList<SyncSeason> list = new ArrayList<>(1);
        list.add(season);
        return seasons(list);
    }

    public SyncShow collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncShow watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncShow ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncShow rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
