package ru.dekabrsky.easylife.game.avatarCustomization.view.fragment

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.easylife.game.avatarCustomization.view.model.CatWidgetSettings

@AddToEndSingle
interface AvatarCustomizationView: BasicView {
    fun updateSettings(avatarSettings: CatWidgetSettings)
    fun showChoiceDialog(title: Int, selectedVariantIndex: Int, variants: Array<String>, callback: (Int) -> Unit)
}