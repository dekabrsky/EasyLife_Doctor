package ru.dekabrsky.easylife.profile.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter

@AddToEndSingle
interface ProfileView : BasicView {

    @OneExecution
    fun setParentCode(code: Int)
    fun setupAvatars(router: FlowRouter)
    fun setTitle(title: String)
}