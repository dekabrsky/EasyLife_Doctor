package ru.dekabrsky.italks.game.view.mapper

import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.view.model.ShelfItemUiModel
import javax.inject.Inject

class ShelfItemsUiMapper @Inject constructor() {
    fun map(level: Int = 4): List<ShelfItemUiModel> {
        return listOf(
            ShelfItemUiModel(R.drawable.cup),
            ShelfItemUiModel(R.drawable.medal),
            ShelfItemUiModel(R.drawable.flowers_pod),
            ShelfItemUiModel(R.drawable.cup),
            ShelfItemUiModel(R.drawable.flowers_pod),
            ShelfItemUiModel(R.drawable.medal),
            ShelfItemUiModel(R.drawable.cup),
            ShelfItemUiModel(R.drawable.cup)
        ).take(level * 2)
    }
}