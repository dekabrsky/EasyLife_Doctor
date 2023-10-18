package ru.dekabrsky.italks.game.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Progress::class], version = 1)
abstract class ProgressDb : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object{
        fun getDb(context: Context): ProgressDb{
            return Room.databaseBuilder(
                context.applicationContext,
                ProgressDb::class.java,
                "progress.db"
            ).build()
        }
    }
}