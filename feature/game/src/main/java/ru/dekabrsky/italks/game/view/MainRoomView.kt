package ru.dekabrsky.italks.game.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.game.view.model.ShelfItemUiModel

@AddToEndSingle
interface MainRoomView: BasicView {
    fun setShelfItems(list: List<ShelfItemUiModel>)
}