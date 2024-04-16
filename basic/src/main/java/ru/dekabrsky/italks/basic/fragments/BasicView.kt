package ru.dekabrsky.italks.basic.fragments

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

@OneExecution
interface BasicView: MvpView {
    fun showError(error: Throwable, action: (() -> Unit)? = null)
    fun showError(error: String)
    fun showToast(msg: String)
    fun setLoadingVisibility(isVisible: Boolean)
}