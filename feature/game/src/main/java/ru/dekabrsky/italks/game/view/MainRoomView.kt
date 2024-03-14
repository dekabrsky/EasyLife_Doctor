package ru.dekabrsky.italks.game.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.game.avatarCustomization.view.model.CatWidgetSettings
import ru.dekabrsky.italks.game.view.model.ItemVisibility
import ru.dekabrsky.italks.game.view.model.ShelfItemUiModel

@AddToEndSingle
interface MainRoomView: BasicView {
    fun setShelfItems(list: List<ShelfItemUiModel>)
    fun updateItemsVisibility(level: Int, itemsVisibility: List<ItemVisibility>)
    fun setupAvatar(router: FlowRouter)
    fun setMusicIsOn(isOn: Boolean)

    @OneExecution
    fun showColorsDialog(selectedVariantIndex: Int, variants: Array<String>)
    fun setRoomColor(res: Int)
    fun setScore(score: String)
    fun scrollToAvatar()
    fun updateCat(settings: CatWidgetSettings)
}