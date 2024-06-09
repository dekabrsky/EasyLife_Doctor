package ru.dekabrsky.easylife.game.view

import android.text.SpannableString
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.game.avatarCustomization.view.model.CatWidgetSettings
import ru.dekabrsky.easylife.game.view.model.ItemVisibility
import ru.dekabrsky.easylife.game.view.model.ShelfItemUiModel

@AddToEndSingle
interface MainRoomView: BasicView {
    fun setShelfItems(list: List<ShelfItemUiModel>)
    fun updateItemsVisibility(level: Int, itemsVisibility: List<ItemVisibility>)
    fun setupAvatar(router: FlowRouter)
    fun setMusicIsOn(isOn: Boolean)

    @OneExecution
    fun showColorsDialog(selectedVariantIndex: Int, variants: List<Pair<SpannableString, Int>>)
    fun setRoomColor(res: Int)
    fun setScore(score: String)
    fun scrollToAvatar()
    fun updateCat(settings: CatWidgetSettings)
}