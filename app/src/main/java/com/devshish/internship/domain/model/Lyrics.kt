package com.devshish.internship.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "lyrics")
data class Lyrics(
    val title: String,
    val artist: String,
    val content: String,
    val imageUri: String?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
