package ru.dekabrsky.italks.game.view.mapper

import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.view.model.ShelfItemUiModel
import javax.inject.Inject

class ShelfItemsUiMapper @Inject constructor() {
    fun map(): List<ShelfItemUiModel> {
        return listOf(
            ShelfItemUiModel(R.drawable.cup),
            ShelfItemUiModel(null),
            ShelfItemUiModel(R.drawable.clock),
            ShelfItemUiModel(R.drawable.cup),
            ShelfItemUiModel(null),
            ShelfItemUiModel(R.drawable.cup),
            ShelfItemUiModel(null)
        )
    }
}