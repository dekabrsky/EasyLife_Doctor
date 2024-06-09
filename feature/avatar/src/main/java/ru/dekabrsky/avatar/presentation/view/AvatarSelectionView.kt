package ru.dekabrsky.avatar.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.avatar.domain.AvatarType
import ru.dekabrsky.easylife.basic.fragments.BasicView

@AddToEndSingle
interface AvatarSelectionView: BasicView {
    fun setAvatars(avatars: List<AvatarType>)
    fun setButtonVisibility(isVisible: Boolean)
    @OneExecution
    fun refreshAvatars()
}