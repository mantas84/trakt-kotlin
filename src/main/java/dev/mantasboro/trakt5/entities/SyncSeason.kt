package dev.mantasboro.trakt5.entities;

import dev.mantasboro.trakt5.enums.Rating;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;

public class SyncSeason {

    public Integer number;
    public List<SyncEpisode> episodes;

    public OffsetDateTime collected_at;
    public OffsetDateTime watched_at;
    public OffsetDateTime rated_at;
    public Rating rating;

    public SyncSeason number(int number) {
        this.number = number;
        return this;
    }

    public SyncSeason episodes(List<SyncEpisode> episodes) {
        this.episodes = episodes;
        return this;
    }

    public SyncSeason episodes(SyncEpisode episode) {
        ArrayList<SyncEpisode> list = new ArrayList<>(1);
        list.add(episode);
        return episodes(list);
    }

    public SyncSeason collectedAt(OffsetDateTime collectedAt) {
        this.collected_at = collectedAt;
        return this;
    }

    public SyncSeason watchedAt(OffsetDateTime watchedAt) {
        this.watched_at = watchedAt;
        return this;
    }

    public SyncSeason ratedAt(OffsetDateTime ratedAt) {
        this.rated_at = ratedAt;
        return this;
    }

    public SyncSeason rating(Rating rating) {
        this.rating = rating;
        return this;
    }

}
