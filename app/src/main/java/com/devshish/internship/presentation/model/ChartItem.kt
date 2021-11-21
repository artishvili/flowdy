package com.devshish.internship.presentation.model

import com.devshish.internship.domain.model.Artist
import com.devshish.internship.domain.model.Track

sealed class ChartItem {

    data class ArtistItem(val artist: Artist) : ChartItem()

    data class TrackItem(val track: Track) : ChartItem()

    object Header : ChartItem()
}
