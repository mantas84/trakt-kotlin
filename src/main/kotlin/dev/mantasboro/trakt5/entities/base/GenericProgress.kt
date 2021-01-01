package dev.mantasboro.trakt5.entities.base

import dev.mantasboro.trakt5.entities.SyncEpisode
import dev.mantasboro.trakt5.entities.SyncMovie
import dev.mantasboro.trakt5.entities.SyncShow

interface GenericProgress {
    var episode: SyncEpisode?
    var show: SyncShow?
    var movie: SyncMovie?
    var progress: Double?
}

data class GenericProgressData(
    override var episode: SyncEpisode? = null,
    override var show: SyncShow? = null,
    override var movie: SyncMovie? = null,
    override var progress: Double? = null,
) : GenericProgress
