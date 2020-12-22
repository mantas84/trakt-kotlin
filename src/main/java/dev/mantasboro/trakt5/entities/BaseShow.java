package dev.mantasboro.trakt5.entities;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

public class BaseShow {

    public Show show;

    /** collection, watched */
    public List<BaseSeason> seasons;

    /** collection */
    public OffsetDateTime last_collected_at;
    /** watchlist */
    public OffsetDateTime listed_at;
    /** watched */
    public Integer plays;
    public OffsetDateTime last_watched_at;
    public OffsetDateTime last_updated_at;
    public OffsetDateTime reset_at;
    /** progress */
    public Integer aired;
    public Integer completed;
    public List<Season> hidden_seasons;
    public Episode next_episode;
    public Episode last_episode;

}
