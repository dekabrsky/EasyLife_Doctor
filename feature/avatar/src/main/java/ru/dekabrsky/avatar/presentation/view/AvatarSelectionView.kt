package ru.dekabrsky.avatar.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.avatar.domain.AvatarType
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter

@AddToEndSingle
interface AvatarSelectionView: BasicView {
    fun setAvatars(avatars: List<AvatarType>)
    fun setButtonVisibility(isVisible: Boolean)
    @OneExecution
    fun refreshAvatars()
}