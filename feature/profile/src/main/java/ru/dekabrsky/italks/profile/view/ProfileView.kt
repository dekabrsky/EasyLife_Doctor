package ru.dekabrsky.italks.profile.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter

@AddToEndSingle
interface ProfileView : BasicView {

    @OneExecution
    fun setParentCode(code: Int)
    fun setupAvatars(router: FlowRouter)
}