package com.devshish.internship.data.db

import androidx.room.*
import com.devshish.internship.data.model.room.RoomSong
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeSong(song: RoomSong)

    @Query("SELECT * FROM lyrics")
    fun getStoredSongs(): Flow<List<RoomSong>>

    @Query("SELECT * FROM lyrics WHERE title = :title AND artist = :artist")
    suspend fun getStoredSong(title: String, artist: String): RoomSong?

    @Query("SELECT EXISTS(SELECT * FROM lyrics WHERE title = :title AND artist = :artist)")
    fun isSongStored(title: String, artist: String): Flow<Boolean>

    @Delete
    suspend fun deleteSong(song: RoomSong)
}
