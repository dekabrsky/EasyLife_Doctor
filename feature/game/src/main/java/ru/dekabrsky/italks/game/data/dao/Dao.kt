package ru.dekabrsky.easylife.game.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.dekabrsky.easylife.game.data.model.Progress

@Dao
interface Dao {
    @Insert
    fun insertProgress(count: Progress)
    @Query("SELECT SUM(count) FROM progress")
    fun getCount(): Flow<Int>
}