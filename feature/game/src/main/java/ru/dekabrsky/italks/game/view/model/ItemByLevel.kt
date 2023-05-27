package ru.dekabrsky.italks.game.view.model

import androidx.annotation.IdRes

class ItemByLevel(
    val level: Int,
    @IdRes val viewId: Int
)

class ItemVisibility(
    @IdRes val viewId: Int,
    val visible: Boolean
)