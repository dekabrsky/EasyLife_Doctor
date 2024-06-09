package ru.dekabrsky.easylife.game.view.mapper

import ru.dekabrsky.easylife.game.R
import ru.dekabrsky.easylife.game.view.model.ItemByLevel
import ru.dekabrsky.easylife.game.view.model.ItemVisibility
import javax.inject.Inject

@Suppress("MagicNumber")
class ItemsVisibilityMapper @Inject constructor() {
    private val itemsByLevel = listOf(
        ItemByLevel(2, R.id.sofa),
        ItemByLevel(2, R.id.hanger),
        ItemByLevel(3, R.id.desk),
        ItemByLevel(3, R.id.lamp),
        ItemByLevel(4, R.id.bush),
        ItemByLevel(4, R.id.coat)
    )
    fun map(level: Int) = itemsByLevel.map {
        ItemVisibility(
            viewId = it.viewId,
            visible = it.level <= level
        )
    }
}