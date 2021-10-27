package com.devshish.internship.presentation.ui.utils

import android.os.Bundle
import com.devshish.internship.domain.model.Song

private const val KEY_SONG = "KEY_SONG"
private const val KEY_POSITION = "KEY_POSITION"

var Bundle.song: Song?
    get() = getSerializable(KEY_SONG) as? Song
    set(value) = putSerializable(KEY_SONG, value)

var Bundle.position: Long
    get() = getLong(KEY_POSITION)
    set(value) = putLong(KEY_POSITION, value)
