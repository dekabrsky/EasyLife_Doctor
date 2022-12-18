package ru.dekabrsky.italks.profile.view

import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.fragments.BasicView

@OneExecution
interface ProfileView : BasicView {

    fun setParentCode(code: Int)
}