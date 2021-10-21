package com.devshish.internship.presentation.ui.utils

import android.os.Bundle
import com.devshish.internship.domain.model.Song

private const val KEY_SONG = "KEY_SONG"

var Bundle.song: Song?
    get() = getSerializable(KEY_SONG) as? Song
    set(value) = putSerializable(KEY_SONG, value)