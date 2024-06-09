package ru.dekabrsky.easylife.game.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "miniGame")
    var miniGame: String,
    @ColumnInfo(name = "count")
    var count: Int,
)