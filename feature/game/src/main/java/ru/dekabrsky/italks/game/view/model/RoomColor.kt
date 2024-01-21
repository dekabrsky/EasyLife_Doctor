package ru.dekabrsky.italks.game.view.model

import androidx.annotation.ColorRes
import ru.dekabrsky.italks.game.R

enum class RoomColor(val rusName: String, @ColorRes val res: Int) {
    Pink("Розовый", R.color.wall_color_pink),
    Cyan("Бирюзовый", R.color.wall_color_cyan),
    Green("Зеленый", R.color.wall_color_green),
    Yellow("Желтый", R.color.wall_color_yellow),
    Purple("Фиолетовый", R.color.wall_color_purple),
    Blue("Синий", R.color.wall_color_blue);

    companion object {
        fun getByRusName(rusName: String) = values().find { it.rusName == rusName } ?: Pink
        fun indexOfRusName(rusName: String) = getByRusName(rusName).ordinal
        fun getByIndex(i: Int) = values().getOrNull(i) ?: Pink
    }
}