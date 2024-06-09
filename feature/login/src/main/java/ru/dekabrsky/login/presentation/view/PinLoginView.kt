package ru.dekabrsky.login.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.easylife.basic.fragments.BasicView


@AddToEndSingle
interface PinLoginView : BasicView {
    fun setAdditionalButtonText(text: String)
    fun setTitle(title: String)
    fun setPinError(error: String)
}