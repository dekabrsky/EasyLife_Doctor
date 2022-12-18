package ru.dekabrsky.login.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface LoginView: BasicView {
    fun setupForRegistration()
    fun setupForLogin()
}