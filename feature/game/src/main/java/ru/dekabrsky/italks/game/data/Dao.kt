package ru.dekabrsky.italks.game.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertProgress(count: Progress)
    @Query("SELECT SUM(count) FROM progress")
    fun getCount(): Flow<Int>
}