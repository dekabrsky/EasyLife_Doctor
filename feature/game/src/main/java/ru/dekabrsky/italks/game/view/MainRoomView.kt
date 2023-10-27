package ru.dekabrsky.italks.game.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.game.view.model.ItemVisibility
import ru.dekabrsky.italks.game.view.model.ShelfItemUiModel

@AddToEndSingle
interface MainRoomView: BasicView {
    fun setShelfItems(list: List<ShelfItemUiModel>)
    fun updateItemsVisibility(level: Int, itemsVisibility: List<ItemVisibility>)
    fun setupAvatar(router: FlowRouter)
}